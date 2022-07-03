package org.fourstack.reactivecricinfo.rankinginfoservice.intg;

import org.fourstack.reactivecricinfo.rankinginfoservice.dao.RankingInfoDao;
import org.fourstack.reactivecricinfo.rankinginfoservice.testUtils.EntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@DataMongoTest
@ActiveProfiles("test")
public class RankingInfoDaoTest {

    @Autowired
    private RankingInfoDao repository;

    @BeforeEach
    public void setUpData() {
        repository.save(EntityGenerator.iccRanking())
                .block();
    }

    @AfterEach
    public void eraseData() {
        repository.deleteAll().block();
    }

    @Test
    @DisplayName("RankingInfoDaoTest: Find IccRanking by Id.")
    public void testFindById(){
        String id = "RKSAC2022-6T8-15L23-9NPZ-50234200";
        var daoObjMono = repository.findById(id);
        StepVerifier.create(daoObjMono)
                .assertNext(daoObj -> {
                    assert daoObj != null;
                    Assertions.assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", daoObj.getPlayerId());
                    Assertions.assertEquals(3, daoObj.getRankings().size());
                }).verifyComplete();

    }

    @Test
    @DisplayName("RankingInfoDaoTest: Find IccRanking by playerId.")
    public void testFindByPlayerId() {
        String playerId = "SAC2022-6T8-15L23-9NPZ-50234200";
        var daoMonoObj = repository.findByPlayerId(playerId);
        StepVerifier.create(daoMonoObj)
                .assertNext(daoObj -> {
                    assert daoObj != null;
                    Assertions.assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", daoObj.getPlayerId());
                    Assertions.assertEquals(3, daoObj.getRankings().size());
                }).verifyComplete();
    }

    @Test
    @DisplayName("RankingInfoDaoTest: Create RankingInfo")
    public void testCreateRankingInfo(){
        var rankingObj = EntityGenerator.iccRanking();
        rankingObj.setRankingId(null);

        var saveObjMono = repository.save(rankingObj);
        StepVerifier.create(saveObjMono)
                .assertNext(savedObj -> {
                    assert savedObj != null;
                    Assertions.assertNotNull(savedObj.getRankingId());
                    Assertions.assertEquals("SAC2022-6T8-15L23-9NPZ-50234200", savedObj.getPlayerId());
                    Assertions.assertEquals(3, savedObj.getRankings().size());
                }).verifyComplete();
    }
}
