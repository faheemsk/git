/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.optum.oss.helper;

import com.optum.oss.dto.EngagementsDTO;
import com.optum.oss.exception.AppException;
import com.optum.oss.model.FindingsModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

/**
 *
 * @author hpasupuleti
 */
@Component
public class CSVFileCreationHelper {

    private static final Logger logger = Logger.getLogger(CSVFileCreationHelper.class);

    private static final String uploadFilePath = System.getProperty("OSS_UPLOAD_FOLDER");

    private CellProcessor[] getSummaryCSVProcessors(String[] finding_header) {
        CellProcessor[] cell;
        cell = new CellProcessor[finding_header.length];
         for( int i=0;i<finding_header.length;i++){
                cell[i]= new Optional();
            }
        final CellProcessor[] processors = cell;
        return processors;
    }

    private CellProcessor[] getEngegementInfoCSVProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
            new Optional(), // Engagement Code
            new Optional(), // Engagement Name
            new Optional(), // Client Name
            new Optional(), // Product Name
            new Optional() // Agreement Date
        };
        return processors;
    }

    private CellProcessor[] getFindingCSVProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
            new Optional(), // Finding ID
            new Optional(), // Finding Name
            new Optional(), // Risk Level
            new Optional(), // Description
            new Optional(), // Recomendation
            new Optional(), // HITRUST
            new Optional(), // HIPAA
            new Optional(), // NIST
            new Optional(), // IRS
            new Optional(), // MARS
            new Optional(), // SOX
            new Optional(), // ISO
            new Optional(), // PCI
            new Optional(), // CSA
            new Optional(), // FISMA
            new Optional(), // GLBA
            new Optional() // FEDRAMP
        };
        return processors;
    }

    private CellProcessor[] getPrioritizationMatrixCSVProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
            new Optional(), // Finding ID
            new Optional(), // Finding Name
            new Optional(), // Risk Level
            new Optional(), // Description
            new Optional(), // Recomendation
        };
        return processors;
    }

    public synchronized File prepareCSVFile(final List findingsList, EngagementsDTO engagementsDTO) throws AppException, IOException {
//        ICsvBeanWriter beanWriter = null;
        ICsvListWriter listWriter = null;
        File _downCSVFile = new File(uploadFilePath + File.separator + "findingsCSV.csv");
        try {

//            beanWriter = new CsvBeanWriter(new FileWriter(_downCSVFile),
//                    CsvPreference.STANDARD_PREFERENCE);
            listWriter = new CsvListWriter(new FileWriter(_downCSVFile),
                    CsvPreference.STANDARD_PREFERENCE);

            final CellProcessor[] eng_processors = getEngegementInfoCSVProcessors();
          

            // the header elements are used to map the bean values to each column (names must match)
//            final String[] finding_header = new String[]{"ID", "Finding", "Description", "Recommendation",
//                "Service", "Source", "Cost Effort", "Domain",
//                "IP Address", "Status", "Scan Identifier", "Scan Start Date",
//                "Scan End Date", "Software Name", "Host", "URL",
//                "Operating System", "Netbios", "MAC Address", "Port",
//                "Techincal details", "Impact Detail", "Root Cause Detail", "CVE ID",
//                "CVE Description", "Overall Score", "Base score", "Temporal score", "Environmental Score",
//                "Severity", "Probability", "Impact",
//                "HITRUST", "HIPAA", "NIST", "IRS",
//                "MARS", "SOX", "ISO", "PCI",
//                "CSA", "FISMA", "GLBA", "FED RAMP"};
            List headerList = (List) findingsList.get(0);
            final String[] finding_header =(String[]) headerList.toArray(new String[headerList.size() ]);
              final CellProcessor[] processors = getSummaryCSVProcessors(finding_header);

            final String[] eng_header = new String[]{"Client Name", "Engagement Code", "Engagement Name", "Product", "Agreement Date"};

            // Start : write the engagement information
            listWriter.writeHeader(eng_header);
            List<Object> engagement_data = Arrays.asList(new Object[]{engagementsDTO.getClientName(), engagementsDTO.getEngagementKey(),
                engagementsDTO.getEngagmentName(), engagementsDTO.getEngagementPkgName(), engagementsDTO.getAgreementDate()});
            listWriter.write(engagement_data, eng_processors);
            // End : write the engagement information
            listWriter.writeHeader("\n");
            listWriter.writeHeader("\n");
            // Start : write the findings information
            listWriter.writeHeader(finding_header);
//            List<String> objectList = new ArrayList<String>(findingsList);
            int count = 0;
            for (Object finding : findingsList) {
                if(count!= 0){
//                List<Object> finding_data = Arrays.asList(new Object[]{finding.getId(), finding.getFinding(), finding.getDescription(), finding.getRecommendation(),
//                    finding.getService(), finding.getSource(), finding.getCostEffort(), finding.getDomain(),
//                    finding.getIpAddress(), finding.getStatus(), finding.getScanIdentifier(), finding.getScanStartDate(),
//                    finding.getScanEndDate(), finding.getSoftwareName(), finding.getHost(), finding.getUrl(),
//                    finding.getOperatingSystem(), finding.getNetBios(), finding.getMacAddress(), finding.getPort(),
//                    finding.getTechnicalDetails(), finding.getImpactDetail(), finding.getRootCauseDetail(), finding.getCveId(),
//                    finding.getCveDescription(), finding.getOverAllScore(), finding.getBaseScore(), finding.getTemporalScore(), finding.getEnvironmentalScore(),
//                    finding.getSeverity(), finding.getProbability(), finding.getImpact(),
//                    finding.getHitrust(), finding.getHipaa(), finding.getNist(), finding.getIrs(),
//                    finding.getMars(), finding.getSox(), finding.getIso(), finding.getPci(),
//                    finding.getCsa(), finding.getFisma(), finding.getGlba(), finding.getFedRamp()});
                    List<Object> o = (List<Object>)finding;
//                    List<Object> finding_data = Arrays.asList(finding);
                listWriter.write(o, processors);
                }
                count++;
            }
            // End : write the findings information

            /*
             // write the findings header
             beanWriter.writeHeader(header);
             // write the beans
             for (final FindingsModel findings : findingsList) {
             beanWriter.write(findings, header, processors);
             }
             */
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : prepareCSVFile(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "prepareCSVFile(): " + e.getMessage());
        } finally {
//            if (beanWriter != null) {
//                beanWriter.close();
//            }
            if (listWriter != null) {
                listWriter.close();
            }
        }
        return _downCSVFile;
    }

    public synchronized File prepareCSVFilePrioritizationMatrix(final List<FindingsModel> findingsList, EngagementsDTO engagementsDTO) throws AppException, IOException {
//        ICsvBeanWriter beanWriter = null;
        ICsvListWriter listWriter = null;
        File _downCSVFile = new File(uploadFilePath + File.separator + "findingsCSV.csv");
        try {

//            beanWriter = new CsvBeanWriter(new FileWriter(_downCSVFile),
//                    CsvPreference.STANDARD_PREFERENCE);
            listWriter = new CsvListWriter(new FileWriter(_downCSVFile),
                    CsvPreference.STANDARD_PREFERENCE);

            final CellProcessor[] eng_processors = getEngegementInfoCSVProcessors();
            final CellProcessor[] processors = getPrioritizationMatrixCSVProcessors();

            // the header elements are used to map the bean values to each column (names must match)
            final String[] finding_header = new String[]{"ID", "Finding", "Risk level", "Description", "Recommendation"};

            final String[] eng_header = new String[]{"Client Name", "Engagement Code", "Engagement Name", "Product", "Agreement Date"};

            // Start : write the engagement information
            listWriter.writeHeader(eng_header);
            List<Object> engagement_data = Arrays.asList(new Object[]{engagementsDTO.getClientName(), engagementsDTO.getEngagementKey(),
                engagementsDTO.getEngagmentName(), engagementsDTO.getEngagementPkgName(), engagementsDTO.getAgreementDate()});
            listWriter.write(engagement_data, eng_processors);
            // End : write the engagement information
            listWriter.writeHeader("\n");
            listWriter.writeHeader("\n");
            // Start : write the findings information
            listWriter.writeHeader(finding_header);
            for (final FindingsModel finding : findingsList) {

                List<Object> finding_data = Arrays.asList(new Object[]{finding.getId(), finding.getFinding(), finding.getRisklevel(), finding.getDescription(), finding.getRecommendation()});
                listWriter.write(finding_data, processors);
            }
            // End : write the findings information

            /*
             // write the findings header
             beanWriter.writeHeader(header);
             // write the beans
             for (final FindingsModel findings : findingsList) {
             beanWriter.write(findings, header, processors);
             }
             */
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exception occured : prepareCSVFile(): " + e.getMessage());
            throw new AppException("Exception occured ehile Excecuting the "
                    + "prepareCSVFile(): " + e.getMessage());
        } finally {
//            if (beanWriter != null) {
//                beanWriter.close();
//            }
            if (listWriter != null) {
                listWriter.close();
            }
        }
        return _downCSVFile;
    }

}
