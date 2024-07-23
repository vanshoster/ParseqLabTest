# ParseqLabTest

## Project Description

This project provides a REST HTTP service for annotating genetic variants using data from the Clinvar database. The main objective is to create a web service that offers annotation information for a given genetic variant. This service is particularly useful in the field of bioinformatics, where identifying genetic variants and their potential impact on phenotypes is essential.

## Technical Task Overview

A significant part of practical work in genomic data analysis involves identifying differences between a sample's genetic material and a reference genome sequence. These differences, known as genetic variants, can indicate changes in protein or RNA structures, leading to phenotype alterations. Annotating genetic variants involves characterizing them based on their potential impact on the phenotype, with the resulting information called annotations.

Numerous databases serve as annotation sources, such as dbSNP, Clinvar, and gnomAD. In practice, it is often necessary to quickly retrieve annotations for specific genetic variants, such as when analyzing an individual's genome sequence. Different sources provide varying methods for accessing annotation data, including web interfaces and local utilities. Given the trend in bioinformatics towards web services, it is advantageous to offer this functionality as an HTTP service.

## Goal

The goal of this project is to create a REST HTTP service that provides annotation information for a given genetic variant from the Clinvar database. For example, a request to `http://example.com/info?rac=NC_00007.14&lap=55146654&rap=55146656&refkey=T` should return all available annotation information from the Clinvar data file.

## Deployment Instructions

### Prerequisites

- Docker installed on your machine
- Git installed on your machine

### Clone the Repository

1. Open your terminal.
2. Clone the repository from GitHub:
   ```sh
   git clone https://github.com/vanshoster/ParseqLabTest.git
   cd ParseqLabTest
   ```

### Build the Docker Image

In the root of the project directory, build the Docker image:

```sh
docker build -t parseqlab-app:latest .
```

### Run the Docker Container

Start a container with the built image:

```sh
docker run -d -p 8080:8080 --name parseqlab-app parseqlab-app:latest
```

### Verify the Deployment

Open your web browser and navigate to http://localhost:8080/first10 to ensure the application is running correctly.


### Usage

To retrieve annotation information for a specific genetic variant, make a GET request to the `/info` endpoint with the appropriate query parameters. For example:

```sh
http://localhost:8080/info?rac=NC_000001.11&lap=926024&rap=926026&refkey=A
```

This request will return JSON with the annotation details for the specified genetic variant:

```json
{
    "rac": "NC_000001.11",
    "lap": 926024,
    "rap": 926026,
    "refkey": "A",
    "vcfId": "1555362",
    "clnsig": "Likely_benign",
    "clnrevstat": "criteria_provided,_single_submitter",
    "clnvc": "single_nucleotide_variant"
}
```

