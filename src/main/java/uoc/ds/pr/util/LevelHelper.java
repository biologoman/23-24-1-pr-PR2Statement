package uoc.ds.pr.util;
import uoc.ds.pr.CTTCompaniesJobsPR2;
public class LevelHelper{
        public static CTTCompaniesJobsPR2.Level getLevel( int numHours ) {
            CTTCompaniesJobsPR2.Level level = CTTCompaniesJobsPR2.Level.BEFINNER;

            if (numHours>=1000) {
                level = CTTCompaniesJobsPR2.Level.EXPERT;
            }
            else if (numHours>=500 && numHours<1000 ) {
                level = CTTCompaniesJobsPR2.Level.SENIOR;
            }
            else if (numHours>=200 && numHours<500)  {
                level =  CTTCompaniesJobsPR2.Level.JUNIOR;
            }
            else if (numHours>=10 && numHours<200 ) {
                level =  CTTCompaniesJobsPR2.Level.INTERN;
            }
            return level;
        }
    }
