<%-- 
    Document   : security
    Created on : Jun 22, 2016, 5:22:13 PM
    Author     : mrejeti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta content="text/html;charset=utf-8" http-equiv="Content-Type" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Optum Security Solutions</title>
            <link rel="stylesheet" href="css/general2.css" />
            <link rel="stylesheet" href="css/multiple-select.css" />
            <link rel="stylesheet" href="css/portlets.css" />
            <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.css">
                <link rel="icon" href="http://uitoolkit.optum.com/uimf2/images/favicon.ico" type="image/x-icon" />
                <style>
                    #ux-ftr {
                        clear: both;
                        margin: 26px 0;
                        padding: 0 0px!important;
                        font-size: 11px;
                    }
                    .ux-vnav {
                        padding-top:17px;
                    }
                    div.t_fixed_header.ui .headtable .hover {
                        cursor: pointer;
                        height: auto;
                    }
                    .searchbg {
                        background:#cccccc;
                        padding-bottom:5px;
                        padding-left:5px
                    }
                    .morecontent span {
                        display: none;
                    }
                    .morelink {
                        display: block;
                    }
                    select {
                        width:60px;
                        margin-bottom:10px
                    }
                    .dataTables_filter input {
                        border: 1px solid #666666;
                        margin-bottom:10px
                    }
                </style>
                <script src="js/jquery-1.8.2.min.js"></script>
                <script src="js/responsive-layout.js" type="text/javascript"></script>
                <script src="js/ux.js"></script><!-- START JavaScript Includes -->
                <script type="text/javascript" src="js/jquery.fixheadertable.js"></script>
                <script type="text/javascript" language="javascript" src="js/jquery.dataTables.js"></script>
                <script language="JavaScript" src="js/FusionCharts.js"></script>
                <script type="text/javascript" src="js/json2.js"></script>
                <script type="text/javascript" src="js/lib.js" ></script>
                <script type="text/javascript">
                    $(document).ready(function() {

                        $('.fixme').fixheadertable({
                            //caption: 'My header is fixed !',
                            sortable: false,
                            zebra: true,
                            resizeCol: false,
                            height: 320
                        });
                        $('.fixme1').fixheadertable({
                            //caption: 'My header is fixed !',

                            //colratio  : [50, 185, 185, 130, , 185,120],  

                            resizeCol: true,
                        });
                        $('.fixme2').fixheadertable({
                            //caption: 'My header is fixed !',

                            colratio: [50, 185, 185, 100, 185, 110, , 170],
                            resizeCol: true,
                        });

                    });
                </script>
                <script type="text/javascript" language="javascript" class="init">


                    $(document).ready(function() {
                        var table = $('#example').DataTable({
                            "paging": true
                                    //"pagingType": "full_numbers"
                        });
                        if ($.browser.webkit) {
                            setTimeout(function() {
                                oTable.fnAdjustColumnSizing();
                            }, 10);
                        }
                        $('a.toggle-vis').on('click', function(e) {
                            e.preventDefault();

                            // Get the column API object
                            var column = table.column($(this).attr('data-column'));

                            // Toggle the visibility
                            column.visible(!column.visible());
                        });
                        $(".paginate_button").click(function()
                        {

                        });
                    });


                </script>
                </HEAD>
                <BODY>

                    <!-- Start: Wrapper -->
                    <div id="ux-wrapper"> 

                        <!--Header starts here-->

                        <div class="ux-head">
                            <div class="ux-head-cntr-logo"> <span class="ux-head-logo"><img src="images/OPTUM-LOGO-Security-Solutions_no-padding.png" alt="Product Name" /></span> </div>
                            <div class="ux-head-cntr-navigation-global">
                                <ul class="ux-unav">
                                    <li class="ux-unav-has-submenu"><span class="ux-head-welcome">Welcome, James Hill</span>
                                        <ul>
                                            <li><a href="#">Profile</a></li>
                                            <li><a href="../login-healthcheck.html">Sign Out</a></li>
                                        </ul>
                                    </li>
                                    <li>DE Healthcare </li>
                                </ul>
                            </div>
                            <div class="ux-head-cntr-navigation-primary">
                                <ul class="ux-pnav">
                                    <li><a href="#">Home</a></li>
                                    <li class="ux-pnav-selected"><a href="security.html">Engagement Name</a></li>
                                </ul>
                            </div>
                        </div>
                        <!--Header ends here--> 

                        <!-- Begin: Secondary Nav --> 

                        <!-- End: Secondary Nav --> 

                        <!-- Begin: Content -->
                        <ul class="ux-brdc ux-margin-left-1t">
                            <li style="width:90px"><a href="#" > Security Solutions </a></li>
                            <li>Security Health Check</li>
                        </ul>

                        <!--Left navigation starts here--> 
                        <!-- <ul class="ux-vnav ux-width-16t">
                    
                      <li class="ux-vnav-selected"><a href="Repository-Dashboard.html">Dashboard</a>
                      <li ><a href="Repository-Instructions.html">Repository Instructions</a>
                      <li><a href="#">Document Repository</a>
                        <ul>
                          <li ><a href="published.html">Manage Documents</a></li>
                          <li><a href="manage-template.html">Manage Templates</a></li>
                          <li><a href="manage-resources.html">Manage Resources</a></li>
                          <li><a href="reports.html">Reports</a></li>
                        <!--<li><a href="manage-attestations.html">Manage Attestations</a></li>
                        
                      </ul>
                    </li>
                    <li><a href="business-agreements.html">Business Agreements</a></li>
                    <li><a href="regulation-database.html">Regulation Database</a></li>
                  </ul>--> 
                        <!--Left navigation ends here--> 

                        <!-- content starts -->
                        <div class="ux-content">
                            <h1>Security Health Check</h1>
                            <div class="ux-panl">
                                <div class="ux-hform">
                                    <dl class="ux-width-21t ux-margin-none ux-padding-bottom-none">
                                        <dt>
                                            <label>Engagement Name: </label>
                                            Hartford Network </dt>
                                        <dd> </dd>
                                    </dl>
                                    <dl class="ux-width-15t ux-margin-none ux-padding-bottom-none">
                                        <dt >
                                            <label>Engagement Code: </label>
                                            HN001-23 </dt>
                                        <dd > </dd>
                                    </dl>
                                </div>
                            </div>
                            <div class="ux-tabs ux-tabs-border-bottm ux-margin-bottom-1t">
                                <ul>
                                    <li class="ux-tabs-selected"><a href="security.html" title="Summary" >Summary</a></li>
                                    <li><a href="redteam.html"  title="Assessments">Assessments</a></li>

                                    <li><a href="threathunting.html" title="Threat Hunting">Threat Hunting</a></li>
                                    <li><a href="reports.html" title="Reports">Reports</a></li>
                                </ul>
                                <span class="ux-float-right"> <a href="images/PDF-Report.pdf" target="_blank"><img src="images/pdf.png" title="Download as a PDF"></a> </span>
                            </div>
                            <div class="ux-clear"></div>
                            <div class="">
                                <div class="ux-panl ux-float-left ux-width-full-inclusive" >
                                    <div class="portlet_topper ux-colr-bg-orange-4">Summary
                                        <ul class="titlemenu">
                                            <li> 
                                                <!--<input type="image" name=""  title="Download" src="images/pdf.png" style="border-width:0px;">
                                          <ul class="the_menu" id="settingmenu" style="display: none; top: 30px; position: absolute;
                                              right: 0; top: 21px;">
                                            <li> <a href="#">Minimize</a></li>
                                            <li> <a href="#">Restore </a></li>
                                            <li> <a href="#">Edit Settings</a> </li>
                                          </ul>--> 
                                            </li>
                                            <li></li>
                                        </ul>
                                    </div>
                                    <div class="ux-panl-content">
                                        <div class="ux-float-left ux-width-46t">
                                            <div class="ux-hform ux-margin-none ux-padding-none">
                                                <dl class="ux-width-13t ux-margin-none ux-padding-none">
                                                    <dt class="ux-width-10t">
                                                        <label>Service</label>
                                                    </dt>
                                                    <dd class="ux-width-12t">
                                                        <select class="ms ux-width-12t" multiple="multiple">
                                                            <option value="0">All</option>
                                                            <option value="5">Red Team</option>
                                                            <option value="6">Network Vulnerabilities Scanning</option>
                                                            <option>Application Vulnerabilities Scanning</option>
                                                            <option>Threat Hunting</option>
                                                        </select>
                                                    </dd>
                                                </dl>
                                                <dl class="ux-width-13t ux-margin-none ux-padding-none">
                                                    <dt class="ux-width-10t">
                                                        <label>Categories</label>
                                                    </dt>
                                                    <dd class="ux-width-10t">
                                                        <select class="ux-width-12t">
                                                            <option value="0">Select</option>
                                                            <option value="0">AC- Access Control</option>
                                                            <option value="0">MP- Media Protection</option>
                                                            <option value="0">AT - Awareness and Training</option>
                                                            <option value="0">PE - Physical and Environmental Protection</option>
                                                            <option value="0">AU- Audit and Accountability</option>
                                                            <option value="0">PL- Planning</option>
                                                            <option value="0">CA- security Assessment and Authorization</option>
                                                            <option value="0">PS- Personnel Security</option>
                                                            <option value="0">CM-Configuration Management</option>
                                                            <option value="0">RA-Risk Management</option>
                                                            <option value="0">CP-Contingency Planning</option>
                                                            <option value="0">SA-System and Services Acquisition</option>
                                                            <option value="0">IA-Identification and Authentication</option>
                                                            <option value="0">SC- System and Communications Protection</option>
                                                            <option value="0">IR-Incident Response</option>
                                                            <option value="0">SI-System and Information Integrity</option>
                                                            <option value="0">MA- Maintenance </option>
                                                            <option value="0">PM- Program Management</option>
                                                        </select>
                                                    </dd>
                                                </dl>
                                                <dl class="u-width-13t ux-margin-none ux-padding-none">
                                                    <dt class="ux-width-10t">
                                                        <label>Severity </label>
                                                    </dt>
                                                    <dd class="ux-width-10t">
                                                        <select class="ux-width-12t ms" multiple="multiple">

                                                            <option value="0">Critical</option>
                                                            <option value="5">High</option>
                                                            <option value="6">Medium</option>
                                                            <option>Low</option>
                                                        </select>
                                                    </dd>
                                                </dl>
                                            </div>
                                            <div class="ux-float-left">
                                                <div id="chartDiv6" align="center"> 
                                                    <script>
                                                        var chartXML = "<chart canvasbgcolor='067c01,ffff00,f40500' canvasbgalpha='100' canvasbgangle='-30'  xAxisName='Impact {br}(Impact to the Business)' clickurl='' legendBorderAlpha='0' legendShadow='0' yAxisName='Likelihood {br}(How frequently the risk presents itself)' showYAxisValues='0' legendposition='right'  basefontsize='11' xAxisNamePadding='10' xAxisLabelDisplay='auto'  captionhorizontalpadding='0' rotateyaxisname='1' centerYaxisName='0' numDivLines='0' showAlternateVGridColor='0'  numberPrefix='' bubbleScale='0.35' showLabels='0' showYAxisValues='0' bgColor='ffffff' borderThickness='0' borderColor='ffffff' canvasBorderThickness='1' canvasBorderColor='666666' borderColor='ffffff'><categories verticalLineColor='666666'  verticalLineAlpha='100' ><category label='1' x='3.4' sL='1'/><category label='2' x='6.7' sL='1'/></categories><dataSet color='f70300' seriesName='Critical' showValues='1' ><set x='9' y='2' z='1' name='6' color='b1e4f9' link='compsumm-6.html#one'/><set x='6' y='5' z='1' name='7' color='b1e4f9' link='compsumm-7.html#one'/><set x='8' y='9' z='1' name='11' color='b1e4f9' link='compsumm-11.html#one'/><set x='7' y='9' z='1' name='10' color='b1e4f9' link='compsumm-10.html#one'/><set x='8' y='6' z='1' name='9' color='b1e4f9' link='compsumm-9.html#one'/><set x='9' y='4' z='1' name='8' color='b1e4f9' link='compsumm-8.html#one'/><set x='9' y='6' z='1' name='5' color='b1e4f9' link='compsumm-5.html#one'/><set x='9' y='7' z='1' name='4' color='b1e4f9' link='compsumm-4.html#one'/><set x='8' y='5' z='1' name='3' color='b1e4f9' link='compsumm-3.html#one'/><set x='9' y='3' z='1' name='2' color='b1e4f9' link='compsumm-2.html#one'/><set x='6' y='8' z='1' name='1' color='b1e4f9' link='compsumm-1.html#one'/></dataSet><dataSet color='f47b29' seriesName='High' showValues='1'><set x='6' y='9' z='1' name='12' color='b1e4f9' link='compsumm-12.html#one'/><set x='9' y='8' z='1' name='13' color='b1e4f9' link='compsumm-13.html#one'/><set x='8' y='8.2' z='1' name='14' color='b1e4f9' link='compsumm-14.html#one'/><set x='8' y='4' z='1' name='15' color='b1e4f9' link='compsumm-15.html#one'/><set x='7' y='6' z='1' name='16' color='b1e4f9' link='compsumm-16.html#one'/><set x='7' y='7.2' z='1' name='17' color='b1e4f9' link='compsumm-17.html#one'/></dataSet><dataSet color='ffff00' seriesName='Medium' showValues='1'><set x='5' y='7' z='1' name='21' color='b1e4f9' link='compsumm-21.html#one'/><set x='6' y='6' z='1' name='20' color='b1e4f9' link='compsumm-20.html#one'/><set x='5' y='5' z='1' name='19' color='b1e4f9' link='compsumm-19.html#one'/><set x='7' y='4' z='1' name='18' color='b1e4f9' link='compsumm-18.html#one'/></dataSet><dataSet color='007f00' seriesName='Low' showValues='1'><set x='3' y='1' z='1' name='22' color='b1e4f9' link='compsumm-22.html#one'/><set x='1' y='2' z='1' name='23' color='b1e4f9' link='compsumm-23.html#one'/></dataSet><trendlines><line startValue='3.6' endValue='3.6' color='666666' displayValue=' ' trendValueAlpha='0'   dashed='0' thickness='1' alpha='100' showOnTop='0'/></trendlines><trendlines><line startValue='6.8' endValue='6.8'  color='666666'  thickness='1' displayValue=' ' alpha='100' showOnTop='0'/></trendlines></chart>";
                                                        var myChart3 = new FusionCharts("swf/Bubble.swf", "myChartId1", "550", "400", "0", "0");
                                                        myChart3.setDataXML(chartXML);
                                                        myChart3.setTransparent('true');
                                                        myChart3.render('chartDiv6');

                                                    </script> 
                                                </div>
                                                <div> </div>

                                                <!-- END Script Block for Chart IncidentTr4 --> 

                                            </div>
                                        </div>
                                        <div class="ux-float-left ux-width-51t">
                                            <div class="ux-panl-content">
                                                <div class="ux-width-24t ux-float-left">
                                                    <div id="chart3div" align="left">Chart will load here</div>
                                                </div>

                                                <div class="ux-width-24t ux-float-left">
                                                    <div id="chart4div" align="right">Chart will load here</div>
                                                </div>
                                                <div class="ux-width-24t ux-float-left">
                                                    <div id="chart5div" align="right">Chart will load here</div>
                                                </div>

                                                <div class="ux-float-left ux-width-24t">
                                                    <div id="chart6div" align="right">Chart will load here</div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="ux-margin-1t">
                                        <div class="ux-clear"></div>
                                        <h2 class="ux-float-left"><a id="one" name="one"></a>Details</h2>
                                        <span class="ux-float-right">
                                            <input type="checkbox" id="chk4" class="ux-margin-left-halft" checked="checked"/>
                                            HITRUST
                                            <input type="checkbox" id="chk1" checked="checked" />
                                            HIPAA

                                            <input type="checkbox" id="chk8" class="ux-margin-left-halft" checked="checked"/>
                                            NIST
                                            <input type="checkbox" id="chk00"  class="ux-margin-left-halft"/>
                                            IRS 1075
                                            <input type="checkbox" id="chk0"  class="ux-margin-left-halft"/>
                                            MARS- E
                                            <input type="checkbox" id="chk2" class="ux-margin-left-halft" />
                                            SOX
                                            <input type="checkbox" id="chk3" class="ux-margin-left-halft" />
                                            GLBA
                                            <input type="checkbox" id="chk5" class="ux-margin-left-halft" />
                                            ISO27001
                                            <input type="checkbox" id="chk6" class="ux-margin-left-halft" />
                                            PCI
                                            <input type="checkbox" id="chk7" class="ux-margin-left-halft " />
                                            FED RAMP
                                            <input type="checkbox" id="chk9" class="ux-margin-left-halft" />
                                            CSA CCM
                                            <input type="checkbox" id="chk10" class="ux-margin-left-halft" />
                                            FISMA Moderate </span>
                                        <table id="example" class="display" cellpadding="0" cellspacing="0" border="0">
                                            <thead>
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Finding</th>
                                                    <th>Risk Level</th>
                                                    <th>Recommendation</th>
                                                    <th>Technical Details</th>
                                                    <th class="hipaa">HITRUST</th>
                                                    <th class="hipaa">HIPAA</th>
                                                    <th class="nist">NIST</th>
                                                    <th class="irs">IRS 1075</th>
                                                    <th class="mars">MARS-E</th>
                                                    <th class="sox">SOX</th>
                                                    <th class="glba">GLBA</th>
                                                    <th class="iso">ISO27001</th>
                                                    <th class="pci">PCI</th>
                                                    <th class="fed">FED RAMP</th>
                                                    <th class="csa">CSA CCM</th>
                                                    <th class="fisma">FISMA Moderate</th>
                                            </thead>
                                            </tr>

                                            <tbody>
                                                <tr>
                                                    <td valign="top">1</td>
                                                    <td valign="top">Internal network assessment: Weak Credentials </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">• Harden the system’s configurations; specifically, ensure that it uses a strong set of credentials.<br />
                                                            <br />
                                                            • Review network to ensure no other such systems are vulnerable.<br />
                                                            <br />
                                                            • Perform periodical or marginal (where significant changes or additions are made) internal network security assessments to reduce exposure of such trivial vulnerabilities to anyone with internal network access.</span></td>
                                                    <td valign="top">Numerous open services, such XXX, with default or weak credentials, allowing attackers to compromise numerous Client assets from the intranet.</td>
                                                    <td valign="top" class="hitrust">01.j</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312</td>
                                                    <td valign="top" class="nist">AC, AU, IA, SC </td>
                                                    <td valign="top" class="irs">AC, AU, IA, SC </td>
                                                    <td valign="top" class="mars">AC, AU, IA, SC </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A9.1, A9.2, A9.4</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, AU, IA, SC </td>
                                                    <td valign="top" class="csa">IAM-12</td>
                                                    <td valign="top" class="fisma">AC, AU, IA, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">2</td>
                                                    <td valign="top">Internal network assessment: Unpatched Systems </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top">Implement a comprehensive patch management process by utilizing applicable regulatory requirements.  Update to the infected systems to the latest patch level.</td>
                                                    <td valign="top">Multiple servers with unpatched known security vulnerabilities were identified.</td>
                                                    <td valign="top" class="hitrust">10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI </td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.2, A12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI </td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">3</td>
                                                    <td valign="top">Social Engineering assessment: Insecure Two Factor Authentication </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top">Implement strong two factor authentication.  To ensure compliance with regulatory requirements, refer to regulatory citation provided.</td>
                                                    <td valign="top">Employees that submitted their credentials along with…Unauthorized access, weak authentication.</td>
                                                    <td valign="top" class="hitrust">10.j</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308, &amp; 312</td>
                                                    <td valign="top" class="nist">AC1, AC7, AC10, AC14, IA1 </td>
                                                    <td valign="top" class="irs">AC1, AC7, AC10, AC14, IA1</td>
                                                    <td valign="top" class="mars">AC1, AC7, AC10, AC14, IA1 </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A9.1, A9.2, A9.4</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC1, AC7, AC10, AC14, IA1 </td>
                                                    <td valign="top" class="csa">IAM-12</td>
                                                    <td valign="top" class="fisma">AC1, AC7, AC10, AC14, IA1 </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">4</td>
                                                    <td valign="top">Internal network assessment: Lack of Network Segmentation </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">• Implement controls at multiple layers within the network architecture. The more layers you can add at each level (e.g. data, application, etc.), the harder it is for a cybercriminal to gain unauthorized access to sensitive information. Of course this has to be manageable from an operations standpoint and it can’t be to the point where business processes come to a grinding halt. <br />
                                                            <br />
                                                            • Apply the rule of least privileged. For example, a third party vendor may need access to your network, but they most likely don’t need access to certain information. Access should only be provided to the user or system that is absolutely needed and nothing else. <br />
                                                            <br />
                                                            • Segment information access based on your security requirements. Define your different zones based on where your sensitive information resides. For example, you want to make sure that sensitive information isn’t easily accessible by a third party that has no need for this access. Take a step back when looking at your network architecture and determine if there’s unnecessary access or too restrictive access in different places. You may be surprised by what you see. </span></td>
                                                    <td valign="top">Internal network design is flat, leaving critical entities in the network completely vulnerable to direct attack once access is granted.</td>
                                                    <td valign="top" class="hitrust">9.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.312</td>
                                                    <td valign="top" class="nist">AC, AU, SC</td>
                                                    <td valign="top" class="irs">AC, AU, SC</td>
                                                    <td valign="top" class="mars">AC, AU, SC</td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A8.1, A13.1, A14.1, A18.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, AU, SC</td>
                                                    <td valign="top" class="csa">IVS-09</td>
                                                    <td valign="top" class="fisma">AC, AU, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">5</td>
                                                    <td valign="top">MS13-081: Windows USB Descriptor Vulnerability</td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">Most customers have automatic updating enabled and will not need to take any action because this security update will be downloaded and installed automatically. Customers who have not enabled automatic updating need to check for updates and install this update manually. For information about specific configuration options in automatic updating, see Microsoft Knowledge Base Article 294871.</span></td>
                                                    <td valign="top"><span class="more">This security update resolves seven privately reported vulnerabilities in Microsoft Windows. The most severe of these vulnerabilities could allow remote code execution if a user views shared content that embeds OpenType or TrueType font files. An attacker who successfully exploited these vulnerabilities could take complete control of an affected system.</span></td>
                                                    <td valign="top" class="hitrust">10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI</td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.2, A12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI</td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">6</td>
                                                    <td valign="top">SSLv3 Enabled</td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">• Disabling SSL V3.0 on the Windows Server hosting Exchange server application won’t affect classical Exchange services, it will only prevent clients that cannot/don’t “speak” TLS (who speak SSL 2.0/3.0 only) to connect to Exchange services using SSL channel.<br />
                                                            </br>
                                                            • All the other clients such as Outlook and IE will continue to work seamlessly with the Exchange services. </span></td>
                                                    <td valign="top"><span class="more">Microsoft Suggested Actions to mitigate or eliminate the SSL 3.0 vulnerability are to disable 3.0 usage on clients (browsers, devices) and servers, although this vulnerability is not a huge security threat, in the sense that the attacker must show up in the middle of a Client &lt;-&gt; Server SSL session to perform his attack.</span></td>
                                                    <td valign="top" class="hitrust">10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI</td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.2, A12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI</td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">7</td>
                                                    <td valign="top">Internal network assessment: Man in The Middle </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">Implement a comprehensive email security solution with the ability to detect malicious activity in real time.  Implement a web security solution with the ability to detect malicious and anomalous activity in real-time.  Educate employees.  Check your user credentials often. </span></td>
                                                    <td valign="top">No countermeasures against common poisoning attacks such …</td>
                                                    <td valign="top" class="hitrust">9.j, 10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI</td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.2, A12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI</td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">8</td>
                                                    <td valign="top">External perimeter assessment: Unpatched Systems </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top">Implement a comprehensive patch management process by utilizing applicable regulatory requirements.  Update to the infected systems to the latest patch level. </td>
                                                    <td valign="top">Multiple servers with unpatched known security vulnerabilities were identified.</td>
                                                    <td valign="top" class="hitrust">10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI</td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.2, A12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI</td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">9</td>
                                                    <td valign="top">External perimeter assessment: Weak Credentials </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">Harden this and any other internet facing service to ensure default credentials are not used anywhere.
                                                            Perform periodical or marginal (where significant changes or additions are made) external network security assessments to reduce exposure of such trivial vulnerabilities to the internet. </span></td>
                                                    <td valign="top">Numerous open services, such XXX, with default or weak credentials, allowing attackers to compromise numerous Client assets from the intranet.</td>
                                                    <td valign="top" class="hitrust">01.j</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312</td>
                                                    <td valign="top" class="nist">AC, AU, IA, SC </td>
                                                    <td valign="top" class="irs">AC, AU, IA, SC </td>
                                                    <td valign="top" class="mars">AC, AU, IA, SC </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A9.1, A9.2, A9.4</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, AU, IA, SC </td>
                                                    <td valign="top" class="csa">IAM-12</td>
                                                    <td valign="top" class="fisma">AC, AU, IA, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">10</td>
                                                    <td valign="top">Advance persistent threat: Malware connection to an external Command &amp; Control </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">Have a layered defense solution for both inbound
                                                            protection and outbound data-theft prevention in
                                                            the event you are compromised.First, you should have a secure email gateway
                                                            that has the ability to inspect for malicious web
                                                            links and attachments to prevent initial infection.
                                                            Second, you should choose a secure web gateway
                                                            that has more than just traditional URL filtering
                                                            and antivirus. To be effective, the solution must
                                                            have real-time threat analysis to detect zeroday
                                                            malware and non-binary-based malware
                                                            (e.g., JavaScripts) to prevent clients from being
                                                            compromised. Third, the solution should have
                                                            strong outbound web detection capabilities to
                                                            detect malicious behavior indicative of a data
                                                            theft operation in process. To complement this,
                                                            the gateway should have the ability to see inside
                                                            encrypted/SSL traffic and attachments so they can
                                                            be properly inspected for potential sensitive data
                                                            or malware. Your solution should also have strong
                                                            DLP capabilities to be able to see when your most
                                                            valuable data is leaving your organization. </span></td>
                                                    <td valign="top"><span class="more">A malware connection to an external Command &amp; Control has been successfully established from within the organization to the Internet over the HTTPS protocol, commonly used for encrypted web traffic, indicating an attacker with the access to establish such a connection are very likely to be successful in their attempts to achieve persistence within the organization.</span></td>
                                                    <td valign="top" class="hitrust">09.ac</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.312 &amp; 308</td>
                                                    <td valign="top" class="nist">AC, CM, PE, SC </td>
                                                    <td valign="top" class="irs">AC, CM, PE, SC </td>
                                                    <td valign="top" class="mars">AC, CM, PE, SC </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A11.1; A13.1, A13.2, A8.1, A12.1, A9.1, A10.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, CM, PE, SC </td>
                                                    <td valign="top" class="csa">IVS-13</td>
                                                    <td valign="top" class="fisma">AC, CM, PE, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">11</td>
                                                    <td valign="top">Social Engineering assessment: Incomplete monitoring </td>
                                                    <td valign="top">Critical</td>
                                                    <td valign="top"><span class="more">Implement Network malware protection on email infrastructure.  Monitor all email contents, isolate possible infected items and generate alerts to investigate.  If needed, implement perimeter protections to prevent connections to malicious sites,  endpoint malware protection. Consider frequent awareness training on anti-phishing, data security and regulations. </span></td>
                                                    <td valign="top">The spear phishing campaign highlighted that several user groups'  email clients are not being scanned prior to delivery; malicious links or malware can be delivered successfully.</td>
                                                    <td valign="top" class="hitrust">09.aa</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312</td>
                                                    <td valign="top" class="nist">AU, PE, SI, SC </td>
                                                    <td valign="top" class="irs">AU, PE, SI, SC </td>
                                                    <td valign="top" class="mars">AU, PE, SI, SC </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A12.4, A9.2, A9.4, A16.1, A18.1, A18.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AU, PE, SI, SC </td>
                                                    <td valign="top" class="csa">IVS-09</td>
                                                    <td valign="top" class="fisma">AU, PE, SI, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">12</td>
                                                    <td valign="top">Wi-Fi Network: Rogue Access Point </td>
                                                    <td valign="top">High</td>
                                                    <td valign="top">Correct settings of Wi-Fi to prevent users from submitting their credentials to a rogue access point, impersonating Client`s Wi-Fi access points. </td>
                                                    <td valign="top">Current settings of the Wi-Fi does not protect users from submitting their credentials to a rogue access point, impersonating to Client`s Wi-Fi access points.</td>
                                                    <td valign="top" class="hitrust">09.ac</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.312 &amp; 308</td>
                                                    <td valign="top" class="nist">AC, CM, PE, SC </td>
                                                    <td valign="top" class="irs">AC, CM, PE, SC </td>
                                                    <td valign="top" class="mars">AC, CM, PE, SC </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A11.1; A13.1, A13.2, A8.1, A12.1, A9.1, A10.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, CM, PE, SC </td>
                                                    <td valign="top" class="csa">IVS-12</td>
                                                    <td valign="top" class="fisma">AC, CM, PE, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">13</td>
                                                    <td valign="top">Web Application: SQL Injection </td>
                                                    <td valign="top">High</td>
                                                    <td valign="top"><span class="more">Perform strict input validation on all user inputs or dynamic parameters before inserting them into queries. Ensure all parameters are properly encapsulated such that they may never affect SQL syntax – if they are string values ensure they are properly quoted and quotes cannot be inserted via parameters. If they are raw data values, such as an integer, verify that they are of the proper data type before inserting them into the query. 
                                                            It is strongly recommended to use prepared statements and parameterized queries, to minimize the exposure of dynamic SQL queries to attackers.
                                                            Further, if this system is provided by a 3rd party developer or library, contact vendor for a patch </span></td>
                                                    <td valign="top"><span class="more">SQL injection attack consists of insertion of a SQL query via input parameters from the client to the application. This, in turn, allows an attacker to modify existing queries, often in such a way that it is logically equivalent to the attacker having direct access to the database itself.
                                                            A successful SQL injection exploit can potentially read sensitive data from the database, modify database data (Insert/Update/Delete), execute administration operations on the database (such as shutdown the DBMS), recover the content of a given file present on the DBMS file system or even, in some cases, issue commands to the operating system. </span></td>
                                                    <td valign="top" class="hitrust">9.j.</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 </td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI </td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A12.6, A14.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI </td>
                                                    <td valign="top" class="csa">TMV-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">14</td>
                                                    <td valign="top">Fraudulent Digital Certificates Could Allow Spoofing</td>
                                                    <td valign="top">High</td>
                                                    <td valign="top"><span class="more">An automatic updater of revoked certificates is included in supported editions of Windows 8, Windows Server 2012, Windows RT, Windows 8.1, Windows RT 8.1, and Windows Server 2012 R2, and for devices running Windows Phone 8 and Windows Phone 8.1. For these operating systems or devices, customers do not need to take any action, because the CTL will be updated automatically. For customers running Windows Server 2003, Microsoft recommends applying the 3050995 update immediately using update management software, by checking for updates using the Microsoft Update service, or by downloading and applying the update manually (see Microsoft Knowledge Base Article 3050995 for details).</span></td>
                                                    <td valign="top"><span class="more">Microsoft is aware of digital certificates that were improperly issued from the subordinate CA, MCS Holdings, which could be used in attempts to spoof content, perform phishing attacks, or perform man-in-the-middle attacks.</span></td>
                                                    <td valign="top" class="hitrust">10.m</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312 </td>
                                                    <td valign="top" class="nist">CA, CM, PL, SI </td>
                                                    <td valign="top" class="irs">CA, CM, PL, SI </td>
                                                    <td valign="top" class="mars">CA, CM, PL, SI </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A12.1, A14.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CA, CM, PL, SI </td>
                                                    <td valign="top" class="csa">CCC</td>
                                                    <td valign="top" class="fisma">CA, CM, PL, SI</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">15</td>
                                                    <td valign="top">Easily Guessed SSH Credentials</td>
                                                    <td valign="top">High</td>
                                                    <td valign="top">Do not use RC4 encryption<br />
                                                        Because this vulnerability relies on a feature of RC4 encryption, the attack can be prevented by using a different encryption algorithm. </td>
                                                    <td valign="top"><span class="more">Passwords sent using SSH with RC4 encryption can be easily cracked by an attacker who is able to capture and replay the session. This problem occurs for three reasons: SSH sessions can be replayed, the RC4 encryption algorithm has some specific weaknesses, and the SSH daemon provides too much information during the authentication phase of the protocol. </span></td>
                                                    <td valign="top" class="hitrust">01.j</td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312 </td>
                                                    <td valign="top" class="nist">AC, AU, IA, SC </td>
                                                    <td valign="top" class="irs">AC, AU, IA, SC </td>
                                                    <td valign="top" class="mars">AC, AU, IA, SC </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A9.1, A9.2, A9.4</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, AU, IA, SC </td>
                                                    <td valign="top" class="csa">IAM-12</td>
                                                    <td valign="top" class="fisma">AC, AU, IA, SC</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">16</td>
                                                    <td valign="top">Physical security: Exposed, functioning network ports found in multiple unused offices</td>
                                                    <td valign="top">High</td>
                                                    <td valign="top">Secure network equipment, secure unused ports </td>
                                                    <td valign="top">Physical security: Exposed, functioning network ports found in multiple unused offices </td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.310</td>
                                                    <td valign="top" class="nist">MA, PE </td>
                                                    <td valign="top" class="irs">MA, PE </td>
                                                    <td valign="top" class="mars">MA, PE </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A.11.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">MA, PE</td>
                                                    <td valign="top" class="csa">DCS-08</td>
                                                    <td valign="top" class="fisma">MA, PE</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">17</td>
                                                    <td valign="top">Physical security: Open Electrical Access</td>
                                                    <td valign="top">High</td>
                                                    <td valign="top">Limit and monitor access to equipment areas.  Keep critical systems separated from our corporate Internet-connected applications to lower security risk. </td>
                                                    <td valign="top">Physical security: Open Electrical Access </td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.310</td>
                                                    <td valign="top" class="nist">MA, PE </td>
                                                    <td valign="top" class="irs">MA, PE </td>
                                                    <td valign="top" class="mars">MA, PE </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A.11.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">MA, PE</td>
                                                    <td valign="top" class="csa">DCS-08</td>
                                                    <td valign="top" class="fisma">MA, PE</td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">18</td>
                                                    <td valign="top">Cross-Site Scripting </td>
                                                    <td valign="top">Medium</td>
                                                    <td valign="top"><span class="more">There are several mitigation techniques:<br />
                                                            [1] Strategy: Libraries or Frameworks
                                                            Use a vetted library or framework that does not allow this weakness to occur, or provides constructs that make it easier to avoid.<br />
                                                            Examples of libraries and frameworks that make it easier to generate properly encoded output include Microsoft's Anti-XSS library, the OWASP ESAPI Encoding module, and Apache Wicket.<br />
                                                            [2] Understand the context in which the data will be used, and the encoding that will be expected. This is especially important when transmitting data between different components, or when generating outputs that can contain multiple encodings at the same time, such as web pages or multi-part mail messages. Study all expected communication protocols and data representations to determine the required encoding strategies.<br />
                                                            For any data that will be output to another web page, especially any data that was received from external inputs, use the appropriate encoding on all non-alphanumeric characters.<br />
                                                            Parts of the same output document may require different encodings, which will vary depending on whether the output is in the:<br />
                                                            [-] HTML body<br />
                                                            [-] Element attributes (such as src=&quot;XYZ&quot;)<br />
                                                            [-] URIs<br />
                                                            [-] JavaScript sections<br />
                                                            [-] Cascading Style Sheets and style property<br />
                                                            Note that HTML Entity Encoding is only appropriate for the HTML body.
                                                            Consult the XSS Prevention Cheat Sheet <br />
                                                            <a href="http://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet" target="_blank" style="word-break:break-all">http://www.owasp.org/index.php/XSS_(Cross_Site_Scripting)_Prevention_Cheat_Sheet</a> for more details on the types of encoding and escaping that are needed. </span></td>
                                                    <td valign="top"><span class="more">Optum has detected that the application does not correctly neutralize user-controllable input to parameter &quot;pageSelection&quot; before it is placed in output that is served as a web page. Affected URL: <a href="www.abc.abc.com" target="_blank">www.abc.abc.com</a></span></td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.312</td>
                                                    <td valign="top" class="nist">SI control family </td>
                                                    <td valign="top" class="irs">SI control family </td>
                                                    <td valign="top" class="mars">SI control family </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A9.1, A9.4, A10.1, A13.2, A18.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">SI control family</td>
                                                    <td valign="top" class="csa">AIS-03</td>
                                                    <td valign="top" class="fisma"></td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">19</td>
                                                    <td valign="top">Open redirect</td>
                                                    <td valign="top">Medium</td>
                                                    <td valign="top">Recommend to map the destination URL input to a value, rather than the actual URL or portion of the URL, and that server side code translate this value to the target URL. </td>
                                                    <td valign="top"><span class="more">An open redirect is an application that takes a parameter and redirects a user to the parameter value without any validation. This vulnerability is used in phishing attacks to get users to visit malicious sites without realizing it. The open redirect is found in the following URL: <a href="www.abc.abc.com" target="_blank">www.abc.abc.com</a></span></td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">CM, RA, SC, SI </td>
                                                    <td valign="top" class="irs">CM, RA, SC, SI </td>
                                                    <td valign="top" class="mars">CM, RA, SC, SI </td>
                                                    <td valign="top" class="sox">X</td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A 14.2 &amp; A 12.6</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">CM, RA, SC, SI </td>
                                                    <td valign="top" class="csa">TVM-02</td>
                                                    <td valign="top" class="fisma">CM, RA, SC, SI </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">20</td>
                                                    <td valign="top">System with malware</td>
                                                    <td valign="top">Medium</td>
                                                    <td valign="top">Incorporate procedures into incident management that allow for isolation of infected systems, forensics and mitigation. </td>
                                                    <td valign="top"><span class="more">• The infection appears to be localized only to SystemName <br />
                                                            • Logging facilities built in to the SystemName are limited and do not provide any additional insight where the IP originated that planted this malware.<br />
                                                            The following malware artifacts were identified on this system:<br />
                                                            <br />
                                                            • Malware Analysis Sample 01<br />
                                                            SHA256: 7145DAF8E1BEBB1760173B77EA204625D80B0F4FF39BC30A0E884CC70BB3E445<br />
                                                            Filename: jquery.js<br />
                                                            The sample identified was hosted on the affected SystemName.  It is a javascript file that was included in the system’s customization object as is synonymous with CVE-2014-3393.  This was verified as part of the template that is presented when the compromised system portal was accessed by a user.  This sample was inserted in the login-form as noted in the below screenshot of the customization object.<br />
                                                            <br />
                                                            • Malware Analysis Sample 02 (externally hosted)<br />
                                                            SHA256: F57125936557ED75CE12A995134D57899AE9AB63E1C095C04C8BBAD88789FBEF<br />
                                                            Filename: jquery01.js<br />
                                                            This sample was also identified on the affected system.  It is a JavaScript file that is included in the system’s customization object but loaded at the title-panel</span></td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308</td>
                                                    <td valign="top" class="nist">AT, AC-4 </td>
                                                    <td valign="top" class="irs">AT, AC-4 </td>
                                                    <td valign="top" class="mars">AT, AC-4 </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A.7.2</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AT, AC-4 </td>
                                                    <td valign="top" class="csa">HRS-09</td>
                                                    <td valign="top" class="fisma">AT, AC-4 </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">21</td>
                                                    <td valign="top">Physical security: ID cards not RFID shielded</td>
                                                    <td valign="top">Medium</td>
                                                    <td valign="top">RFID card shields </td>
                                                    <td valign="top">Physical security: ID cards not RFID shielded </td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa">45 CFR 164.308 &amp; 312</td>
                                                    <td valign="top" class="nist">AC, IA, PE </td>
                                                    <td valign="top" class="irs">AC, IA, PE </td>
                                                    <td valign="top" class="mars">AC, IA, PE </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A.9.1.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">AC, IA, PE </td>
                                                    <td valign="top" class="csa">BCR</td>
                                                    <td valign="top" class="fisma">AC, IA, PE </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">22</td>
                                                    <td valign="top">Cacheable SSL Page Found</td>
                                                    <td valign="top">Low</td>
                                                    <td valign="top">Review  pages and prevent caching of SSL pages by adding &quot;Cache-Control: no-store&quot; and &quot;Pragma: no-cache&quot; headers to their response: <a href="www.abc.abc.com" target="_blank">www.abc.abc.com</a></td>
                                                    <td valign="top">Web pages are cached at the user browsers.</td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa"></td>
                                                    <td valign="top" class="nist">SM, SA, SC </td>
                                                    <td valign="top" class="irs">SM, SA, SC </td>
                                                    <td valign="top"  class="mars">SM, SA, SC </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.1, A18.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">SM, SA, SC</td>
                                                    <td valign="top" class="csa">GRM-01</td>
                                                    <td valign="top" class="fisma">SM, SA, SC </td>
                                                </tr>
                                                <tr>
                                                    <td valign="top">23</td>
                                                    <td valign="top">Missing HttpOnly Attribute in Session Cookie</td>
                                                    <td valign="top">Low</td>
                                                    <td valign="top">Establish a detail security configuration, which should include the addition of the 'HttpOnly' attribute to all session cookies.  To ensure a robust and complete baseline has been established refer to the regulations provided.</td>
                                                    <td valign="top">During the application test, it was detected that the tested web application set a cookie &quot;jsessionid&quot; without the &quot;HttpOnly&quot; attribute. </td>
                                                    <td valign="top" class="hitrust"></td>
                                                    <td valign="top" class="hipaa"></td>
                                                    <td valign="top" class="nist">SM, SA, SC </td>
                                                    <td valign="top" class="irs">SM, SA, SC</td>
                                                    <td valign="top" class="mars">SM, SA, SC </td>
                                                    <td valign="top" class="sox"></td>
                                                    <td valign="top" class="glba"></td>
                                                    <td valign="top" class="iso">A14.1, A18.1</td>
                                                    <td valign="top" class="pci"></td>
                                                    <td valign="top" class="fed">SM, SA, SC</td>
                                                    <td valign="top" class="csa">GRM-01</td>
                                                    <td valign="top" class="fisma">SM, SA, SC </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>





                                <!-- content ends --> 

                                <!-- Begin: Footer -->
                                <div id="ux-ftr">
                                    <p> &copy; Optum 2016, Inc. - All Rights Reserved </p>
                                </div>
                                <!-- End: Footer --> 
                            </div>  </div>
                        <!-- End: Wrapper --> 

                        <!-- JavaScript includes should go down here at the bottom of the page's source. --> 
                        <script src="js/jquery.multiple.select.js"></script> 
                        <script>

                                                        $(".ms").multipleSelect({
                                                            filter: false,
                                                            multiple: true,
                                                            multipleWidth: 160,
                                                            placeholder: "Select"
                                                        });
                                                        $("#ms1").multipleSelect({
                                                            filter: false,
                                                            multiple: true,
                                                            multipleWidth: 160,
                                                            placeholder: "Select"
                                                        });
                                                        $("#ms2").multipleSelect({
                                                            filter: false,
                                                            multiple: true,
                                                            multipleWidth: 160,
                                                            placeholder: "Select"
                                                        });
                        </script> 
                        <script type="text/javascript" src="js/stacked.js" ></script> 
                        <script type="text/javascript" src="js/rca.js" ></script> 
                        <script type="text/javascript">
                                                        if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i) == 0)
                                                            FusionCharts.setCurrentRenderer(GALLERY_RENDERER);
                                                        var chart = new FusionCharts("swf/StackedBar2D.swf", "ChartId", "605", "400", "0", "0");
                                                        chart.setXMLData(dataStringSecurity);
                                                        chart.render("chartdiv1");

                                                        var chart = new FusionCharts("swf/Marimekko.swf", "ChartId", "605", "400", "0", "0");
                                                        chart.setXMLData(dataStringSecurity);
                                                        chart.render("chartdiv");

                                                        var chart1 = new FusionCharts("swf/Pie2D.swf", "Chart1Id", "100%", "210", "0", "0");
                                                        chart1.setXMLData(dataStringRedPie3);
                                                        chart1.render("chart3div");

                                                        var chart1 = new FusionCharts("swf/Pie2D.swf", "Chart1Id", "100%", "210", "0", "0");
                                                        chart1.setXMLData(dataStringNetPie3);
                                                        chart1.render("chart4div");
                                                        var chart1 = new FusionCharts("swf/Pie2D.swf", "Chart1Id", "100%", "210", "0", "0");
                                                        chart1.setXMLData(dataStringAppPie3);
                                                        chart1.render("chart5div");
                                                        var chart1 = new FusionCharts("swf/Pie2D.swf", "Chart1Id", "100%", "210", "0", "0");
                                                        chart1.setXMLData(dataStringThretPie3);
                                                        chart1.render("chart6div");

                        </script> 
                        <script>
                            $(document).ready(function() {
                                $("#ux-outline-example").click(function() {
                                    $(".ux-snav").fadeOut().fadeIn();
                                });
                            });
                        </script> 
                        <script>
                            $(document).ready(function() {

                                function phoneLayout() {
                                    // Phone layout (not used)
                                }

                                function tabletLayout() {
                                    // Tablet layout (AKA portrait layout)
                                }

                                function desktopLayout() {
                                    // Desktop layout (AKA landscape layout)
                                }

                                //ResponsiveLayout(phoneLayout, tabletLayout, desktopLayout);
                            });
                        </script> 

                        <!-- END JavaScript Includes -->
                </BODY>
                </HTML>
