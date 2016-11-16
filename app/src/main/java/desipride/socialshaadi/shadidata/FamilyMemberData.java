package desipride.socialshaadi.shadidata;

import java.util.ArrayList;

import desipride.socialshaadi.R;

/**
 * Created by parth.mehta on 9/15/15.
 */
public class FamilyMemberData {
    static private ArrayList<FamilyMember> ishanFamilyMembers = null;
    static private ArrayList<FamilyMember> priyaFamilyMembers = null;

    private static final String ISHAN_MOTHER_NAME = "Archana Pande";
    private static final String ISHAN_MOTHER_RELATION = "Ishan's Mother";
    private static final String PARTH_MOTHER_INFO = "";
    private static final int ISHAN_MOTHER_IMAGE = R.drawable.archana_pande_small;

    private static final String ISHAN_FATHER_NAME = "Jayant Pande";
    private static final String ISHAN_FATHER_RELATION = "Ishan's Father";
    private static final String PARTH_FATHER_INFO = "";
    private static final int ISHAN_FATHER_IMAGE = R.drawable.jayant_pande_small_1;

    private static final String ISHAN_AJJI_NAME = "Prabha Pande a.k.a Ajji";
    private static final String ISHAN_AJJI_RELATION = "Ishan's Grandmother";
    private static final String PARTH_BA_INFO = "";
    private static final int ISHAN_AJJI_IMAGE = R.drawable.prabha_pande_photo_small;

    private static final String MUGDHA_MOTHER_NAME = "Neela Dongre";
    private static final String MUGDHA_MOTHER_RELATION = "Mughda's Mother";
    private static final String MUGDHA_MOTHER_INFO = "";
    private static final int PRIYA_MOTHER_IMAGE = R.drawable.neela_dongre_small;

    private static final String MUGDHA_FATHER_NAME = "Nandan Dongre";
    private static final String MUGDHA_FATHER_RELATION = "Mugdha's Father";
    private static final String MUGDHA_FATHER_INFO = "";
    private static final int PRIYA_FATHER_IMAGE = R.drawable.nandan_dongre_small;

    private static final String MUGDHA_SISTER_NAME = "Shubhangi Dongre";
    private static final String MUGDHA_SISTER_RELATION = "Mugdha's Sister";
    private static final String MUGDHA_SISTER_INFO = "";
    private static final int PRIYA_SISTER_IMAGE = R.drawable.shubangi_small;

    private static final String MUGDHA_AJJI_NAME = "Shubadha Bhagwat a.k.a Ajji";
    private static final String MUGDHA_AJJI_RELATION = "Mugdha's Grandmother";
    private static final String MUGDHA_AJJI_INFO = "";
    private static final int MUGHDA_AJJI_IMG = R.drawable.shubhadha_ajji_small;


    public static FamilyMember getIshanFamilyMember(int index) {
        return getIshanFamilyMembers().get(index);
    }

    public static ArrayList<FamilyMember> getIshanFamilyMembers() {
        if(ishanFamilyMembers == null) {
            ishanFamilyMembers = new ArrayList<FamilyMember>(3);
            FamilyMember familyMember = null;

            familyMember = new FamilyMember(ISHAN_MOTHER_NAME, ISHAN_MOTHER_RELATION,PARTH_MOTHER_INFO, ISHAN_MOTHER_IMAGE);
            ishanFamilyMembers.add(familyMember);

            familyMember = new FamilyMember(ISHAN_FATHER_NAME, ISHAN_FATHER_RELATION,PARTH_FATHER_INFO, ISHAN_FATHER_IMAGE);
            ishanFamilyMembers.add(familyMember);

            familyMember = new FamilyMember(ISHAN_AJJI_NAME, ISHAN_AJJI_RELATION,PARTH_BA_INFO, ISHAN_AJJI_IMAGE);
            ishanFamilyMembers.add(familyMember);
        }
        return ishanFamilyMembers;
    }

    public static ArrayList<FamilyMember> getPriyaFamilyMembers() {
        if(priyaFamilyMembers == null) {
            priyaFamilyMembers = new ArrayList<FamilyMember>(4);
            FamilyMember familyMember = null;
            familyMember = new FamilyMember(MUGDHA_AJJI_NAME, MUGDHA_AJJI_RELATION, MUGDHA_AJJI_INFO, MUGHDA_AJJI_IMG);
            priyaFamilyMembers.add(familyMember);
            familyMember = new FamilyMember(MUGDHA_MOTHER_NAME, MUGDHA_MOTHER_RELATION, MUGDHA_MOTHER_INFO,PRIYA_MOTHER_IMAGE);
            priyaFamilyMembers.add(familyMember);
            familyMember = new FamilyMember(MUGDHA_FATHER_NAME, MUGDHA_FATHER_RELATION, MUGDHA_FATHER_INFO,PRIYA_FATHER_IMAGE);
            priyaFamilyMembers.add(familyMember);
            familyMember = new FamilyMember(MUGDHA_SISTER_NAME, MUGDHA_SISTER_RELATION, MUGDHA_SISTER_INFO,PRIYA_SISTER_IMAGE);
            priyaFamilyMembers.add(familyMember);
        }
        return priyaFamilyMembers;
    }

}
