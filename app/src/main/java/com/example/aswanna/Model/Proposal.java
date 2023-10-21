package com.example.aswanna.Model;

public class Proposal {

    private String pid;

    private String documentID;
    private String farmerID;

    private String projectName;
    private String projectType;
    private String projectLocation;
    private String projectDurationInMonths;
    private String projectDescription;
    private String fundingRequired;
    private String expectedReturnsOnInvestment;
    private String imageOneLink;
    private String imageTwoLink;



    private String Status;

    private String postedDate;

    // Constructors
    public Proposal() {

    }

    public Proposal(String PID, String documentID, String farmerID, String projectName, String projectType, String projectLocation, String projectDurationInMonths, String projectDescription, String fundingRequired, String expectedReturnsOnInvestment, String imageOneLink, String imageTwoLink, String status, String postedDate) {
        this.pid = PID;
        this.documentID = documentID;
        this.farmerID = farmerID;
        this.projectName = projectName;
        this.projectType = projectType;
        this.projectLocation = projectLocation;
        this.projectDurationInMonths = projectDurationInMonths;
        this.projectDescription = projectDescription;
        this.fundingRequired = fundingRequired;
        this.expectedReturnsOnInvestment = expectedReturnsOnInvestment;
        this.imageOneLink = imageOneLink;
        this.imageTwoLink = imageTwoLink;
        Status = status;
        this.postedDate = postedDate;
    }

    public String getPID() {
        return pid;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getFarmerID() {
        return farmerID;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public String getProjectDurationInMonths() {
        return projectDurationInMonths;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public String getFundingRequired() {
        return fundingRequired;
    }

    public String getExpectedReturnsOnInvestment() {
        return expectedReturnsOnInvestment;
    }

    public String getImageOneLink() {
        return imageOneLink;
    }

    public String getImageTwoLink() {
        return imageTwoLink;
    }

    public String getStatus() {
        return Status;
    }

    public String getPostedDate() {
        return postedDate;
    }

    // Getters and setters

}

