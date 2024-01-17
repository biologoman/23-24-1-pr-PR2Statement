package uoc.ds.pr.util;

import org.junit.Assert;
import org.junit.Test;
import uoc.ds.pr.CTTCompaniesJobsPR2;

public class LevelHelperTest {

        @Test
        public void levelHelperTest() {
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.SENIOR, LevelHelper.getLevel(999));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.INTERN, LevelHelper.getLevel(35));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.INTERN, LevelHelper.getLevel(18));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.JUNIOR, LevelHelper.getLevel(250));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.JUNIOR, LevelHelper.getLevel(499));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.SENIOR, LevelHelper.getLevel(500));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.SENIOR, LevelHelper.getLevel(999));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, LevelHelper.getLevel(1000));
            Assert.assertEquals(CTTCompaniesJobsPR2.Level.EXPERT, LevelHelper.getLevel(2500));
        }
}
