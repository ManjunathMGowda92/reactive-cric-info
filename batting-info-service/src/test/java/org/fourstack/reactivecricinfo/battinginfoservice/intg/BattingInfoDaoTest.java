package org.fourstack.reactivecricinfo.battinginfoservice.intg;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class BattingInfoDaoTest {

    @Autowired
    BattingInfoDao dao;

    @BeforeEach
    public void setUpData() {
        dao.save(EntityGenerator.battingInfoDAO())
                .block();
    }

    @AfterEach
    public void eraseData() {
        dao.deleteAll()
                .block();
    }

    @Test
    @DisplayName("BattingInfoDaoTest: find all BattingInfo")
    public void testFindAll() {
        var daoObjFlux = dao.findAll();
        StepVerifier.create(daoObjFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @DisplayName("BattingInfoDaoTest: find BattingInfo By Id")
    public void testFindById() {
        String id = "BAT-SAC2022-6T8-15L23-9NPZ-50234200";
        var daoMono = dao.findById(id);
        StepVerifier.create(daoMono)
                .assertNext(obj -> {
                    assert obj != null;
                    assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", obj.getPlayerId());
                    assertEquals(3, obj.getStatistics().size());
                }).verifyComplete();
    }

    @Test
    @DisplayName("BattingInfoDaoTest: find BattingInfo by Player Id")
    public void testFindByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        var daoMono = dao.findByPlayerId(playerId);

        StepVerifier.create(daoMono)
                .assertNext(obj -> {
                    assert obj != null;
                    assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", obj.getPlayerId());
                    assertEquals(3, obj.getStatistics().size());
                }).verifyComplete();
    }

    @Test
    @DisplayName("BattingInfoDaoTest: Test case to save BattingInfo")
    public void testSaveBattingInfo() {
        var battingInfo = EntityGenerator.battingInfoDAO();
        battingInfo.setId(null);
        battingInfo.setPlayerId("SAC2022-6T8-15L23-9NPZ");

        var saveObj = dao.save(battingInfo);
        StepVerifier.create(saveObj)
                .assertNext(obj -> {
                    assert obj != null;
                    assertNotNull(obj.getId());
                    assertEquals("SAC2022-6T8-15L23-9NPZ", obj.getPlayerId());
                    assertEquals(3, obj.getStatistics().size());
                }).verifyComplete();
    }
}
