package com.cse.cseprojectroommanagementserver.domain.reservation.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

@Repository
@Slf4j
public class NamedLockRepository {
    private static final String GET_LOCK = "SELECT GET_LOCK(?, ?)";
    private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";
    private static final String EXCEPTION_MESSAGE = "LOCK 을 수행하는 중에 오류가 발생하였습니다.";

    private final DataSource dataSource;

    public NamedLockRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T executeWithLock(String lockName, int timeoutSeconds, Supplier<T> supplier) {

        try (Connection connection = dataSource.getConnection()) {
            try {
                log.info("start getLock=[{}], timeoutSeconds : [{}], connection=[{}]", lockName, timeoutSeconds, connection);
                getLock(connection, lockName, timeoutSeconds);
                log.info("success getLock=[{}], timeoutSeconds : [{}], connection=[{}]", lockName, timeoutSeconds, connection);

                return supplier.get();
            } finally {
                log.info("start releaseLock=[{}], connection=[{}]", lockName, connection);
                releaseLock(connection, lockName);
                log.info("success releaseLock=[{}], connection=[{}]", lockName, connection);
            }
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void getLock(Connection connection,
                         String lockName,
                         int timeoutseconds) throws SQLException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
            preparedStatement.setString(1, lockName);
            preparedStatement.setInt(2, timeoutseconds);

            checkResultSet(lockName, preparedStatement, "GetLock_");
        }
    }

    private void releaseLock(Connection connection,
                             String lockName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
            preparedStatement.setString(1, lockName);

            checkResultSet(lockName, preparedStatement, "ReleaseLock_");
        }
    }

    private void checkResultSet(String lockName,
                                PreparedStatement preparedStatement,
                                String type) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                log.error("Named LOCK 쿼리 결과 값이 없습니다. type = [{}], reserveLockName : [{}], connection=[{}]", type, lockName, preparedStatement.getConnection());
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
            int result = resultSet.getInt(1);
            if (result != 1) {
                log.error("Named LOCK 쿼리 결과 값이 1이 아닙니다. type = [{}], result : [{}] reserveLockName : [{}], connection=[{}]", type, result, lockName, preparedStatement.getConnection());
                throw new RuntimeException(EXCEPTION_MESSAGE);
            }
        }
    }
}