package com.junyangcompany.demo.entity.professerEntity;

import com.junyangcompany.demo.entity.EnrollCollegeEnrollBatch;
import com.junyangcompany.demo.entity.EnrollStudentPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.util.List;

/**
 * 生涯测评师初选结果
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalBean implements Comparable<ProfessionalBean> {

    private EnrollCollegeEnrollBatch enrollCollegeEnrollBatch;

    private Long seq; // 位次

    private Boolean scienceAndArt; // 文理科

    private Long provinceId;

    private String collegeName;

    private String batchName;

    private String collegeCode;

    private List<CollegeLine> collegeLines;

    private Integer probability;

    private Integer maxProbability;

    private Integer minProbability;

    private String type;

    private Integer rank;

    private Integer minRank;

    private Integer maxRank;

    private List<String> levels;

   private List<PlanLine> planLInes;

    /**
     * 考生信息
     */
   @ManyToOne
   private Examinee examinee;


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(ProfessionalBean o) {
        return this.getRank().compareTo(o.getRank());
    }

    public class CollegeLine
    {
        public Integer minScore;
        public Integer minRank;
        public Integer enrollCount;
        public Integer year;
    }
    public class PlanLine
    {
        public EnrollStudentPlan enrollStudentPlan;
        public  List<CollegeLine> collegeLines;
    }



}
