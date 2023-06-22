package com.gui.test.common.product;

import java.io.Serializable;
import java.util.*;

/**
 * Have information about organization manufacturer this product
 *
 * @see Product
 * @see OrganizationType
 */
public class Organization implements Serializable {
    /**
     * value this field must be more than 0
     * value this field must be unique
     * value this field must be generated automatically
     */
    private int id;
    /**
     * Field cannot be null
     * String cannot be empty
     */
    private String name;
    /**
     * Field cannot be null
     * value this field must be more than 1267
     */
    private String fullName;
    /**
     * Field cannot be null
     * value this field must be more than 0
     */
    private Integer annualTurnover;
    /**
     * Field cannot be null
     * value this field must be more than 0
     */
    private int employeesCount;
    /**
     * Field cannot be null
     */
    private OrganizationType type;

    public Organization() {
    }

    public Organization(int id, String name, String fullName, Integer annualTurnover, int employeesCount, OrganizationType type) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.annualTurnover = annualTurnover;
        this.employeesCount = employeesCount;
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAnnualTurnover(Integer annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getAnnualTurnover() {
        return annualTurnover;
    }

    public int getEmployeesCount() {
        return employeesCount;
    }

    public OrganizationType getType() {
        return type;
    }

    /**
     * @param organization compare with this organization by id
     * @return boolean
     */

    public int compareTo(Organization organization) {
        int res = this.fullName.compareTo(organization.getFullName());
        if (res > 0) return 1;
        else if (res == 0) return 0;
        return -1;
    }

    /**
     * method do sorting by id
     *
     * @param organizations unsorted list of organizations
     * @return ArrayList<Organization>
     */
    public static ArrayList<Organization> sort(ArrayList<Organization> organizations) {
        var sortedOrganizations = new ArrayList<Organization>();
        ArrayList<Integer> id = new ArrayList<>();
        HashMap<Integer, Organization> organizationMap = new HashMap<>();

        for (Organization organization : organizations) {
            id.add(organization.getId());
            organizationMap.put(organization.getId(), organization);
        }
        id.sort(Comparator.naturalOrder());
        for (Integer key : id) {
            sortedOrganizations.add(organizationMap.get(key));
        }
        return sortedOrganizations;
    }
    
    public static OrganizationType stringToOrgType(String s) {
        switch (s) {
            case "COMMERCIAL", "C" -> {return OrganizationType.COMMERCIAL;}
            case "GOVERNMENT", "G" -> {return OrganizationType.GOVERNMENT;}
            case "TRUST", "T" -> {return OrganizationType.TRUST;}
            case "PRIVATE_LIMITED_COMPANY", "P" -> {return OrganizationType.PRIVATE_LIMITED_COMPANY;}
            default -> {return  null;}
        }
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", annualTurnover=" + annualTurnover +
                ", employeesCount=" + employeesCount +
                ", type=" + type +
                '}';
    }
}