package org.fourstack.reactivecricinfo.battinginfoservice.intg;

import org.fourstack.reactivecricinfo.battinginfoservice.dao.BattingInfoDao;
import org.fourstack.reactivecricinfo.battinginfoservice.util.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

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
                    Assertions.assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", obj.getPlayerId());
                    Assertions.assertEquals(3, obj.getStatistics().size());
                }).verifyComplete();
    }
}
