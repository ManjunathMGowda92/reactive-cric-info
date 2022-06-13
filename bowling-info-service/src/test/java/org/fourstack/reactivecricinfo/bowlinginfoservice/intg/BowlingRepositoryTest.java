package org.fourstack.reactivecricinfo.bowlinginfoservice.intg;

import org.fourstack.reactivecricinfo.bowlinginfoservice.dao.BowlingInfoDao;
import org.fourstack.reactivecricinfo.bowlinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class BowlingRepositoryTest {

    @Autowired
    private BowlingInfoDao dao;

    @BeforeEach
    public void setUpData() {
        dao.save(EntityGenerator.bowlingInfoDAO())
                .block();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll().block();
    }

    @Test
    @DisplayName("BowlingRepositoryTest: Find BowlingInfo by PlayerId")
    public void testFindByPlayerId() {
        var bowlingInfo = dao.findByPlayerId("SAC2022-6T8-15L23-9NPZ-50234200");

        StepVerifier.create(bowlingInfo)
                .assertNext(daoObj -> {
                    assert daoObj != null;
                    assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", daoObj.getPlayerId());
                    assertEquals(3, daoObj.getStatistics().size());
                }).verifyComplete();
    }

    @Test
    @DisplayName("BowlingRepositoryTest: Find BowlingInfo by Id")
    public void testFindById() {
        var bowlingInfo = dao.findById("BOWLSAC2022-6T8-15L23-9NPZ-50234200");

        StepVerifier.create(bowlingInfo)
                .assertNext(daoObj -> {
                    assert  daoObj != null;
                    assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", daoObj.getPlayerId());
                    assertEquals(3, daoObj.getStatistics().size());
                });
    }

    @Test
    @DisplayName("BowlingRepositoryTest: Create Bowling Info")
    public void testCreateBowlingInfo() {
        var daoObj = EntityGenerator.bowlingInfoDAO();
        daoObj.setBowlingId(null);
        var savedObj = dao.save(daoObj);
        StepVerifier.create(savedObj)
                        .assertNext(obj -> {
                            assert obj != null;
                            assertNotNull(obj.getBowlingId());
                            assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", obj.getPlayerId());
                            assertEquals(3, obj.getStatistics().size());
                        });
    }

    @Test
    @DisplayName("BowlingRepositoryTest: Delete BowlingInfo by Id.")
    public void testDeleteById() {
        var returnedObj = dao.deleteById("BOWLSAC2022-6T8-15L23-9NPZ-50234200");

        // As deleteById method returns just Mono<Void> use verifyComplete() to verify it.
        StepVerifier.create(returnedObj)
                .verifyComplete();
    }

    @Test
    public void testDeleteByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";

        // block method is used to delete the dao Object, otherwise it
        // will return the object in the next call (as it is async call).
        dao.deleteByPlayerId(playerId).block();

        // Check if the BowlingInfo Exists.
        var daoObj = dao.findByPlayerId(playerId);

        StepVerifier.create(daoObj)
                .verifyComplete();
    }
}
