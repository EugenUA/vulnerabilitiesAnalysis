import entities.aggregationEntities.Email;
import entities.aggregationEntities.HTML;
import entities.aggregationEntities.RSS;
import org.junit.Before;
import org.junit.Test;
import service.TextMining.TextMiningMainEntrance;

import java.util.ArrayList;

public class JUnitTextMiningMainEntrance {

    public Email vmWareEmail = new Email();
    public Email debianEmail = new Email();
    public Email fdEmail = new Email();
    public Email getVmWareEmailDup = new Email();

    public Email nonValid = new Email();

    public RSS usCertAlert = new RSS();
    public RSS ciscoAlert = new RSS();
    public RSS bugtraqAlert = new RSS();

    @Before
    public void setUp() throws Exception {

        nonValid.setContent("Greetings from hardwear.io!\n" +
                "\n" +
                "We would like to share few exciting updates that you can expect from\n" +
                "hardwear.io in 2018!\n" +
                "\n" +
                "First of all, we are very proud to announce that hardwear.io is going to\n" +
                "hold its first Security Training in Berlin!\n" +
                "\n" +
                "Dates: 26 – 27 April 2018\n" +
                "\n" +
                "Venue: Novotel Am Tiergarten, Berlin, Germany\n" +
                "\n" +
                "Hardware Security Trainings:\n" +
                "\n" +
                "-  Practical IOT Hacking by Aseem Jakhar\n" +
                "\n" +
                "-  Low-Level Hardware Reversing by Javier-Vazquez Vidal & Ferdinand\n" +
                "\n" +
                "-  Side-Channel Attacks 101 by Lejla Batina & Kostas Papagiannopoulos\n" +
                "\n" +
                "-  Practical Car Hacking by Guillaume Heilles\n" +
                "\n" +
                "Registration is Open. Pre-con proces available till 31st March 2018.\n" +
                "\n" +
                "\n" +
                "Hardwear.io Conference & Training 2018 will traditionally take place in The\n" +
                "Hague for the 4th time! Mark your calendars to:\n" +
                "\n" +
                "Training: 11-12 September\n" +
                "\n" +
                "Conference: 13-14 September\n" +
                "\n" +
                "Venue: NH Hotels, The Hague, the Netherlands\n" +
                "\n" +
                "\n" +
                "hardwear.io 2018 Call For Papers is Open till 9th May 2018! Get your\n" +
                "research ready for another successful year!\n" +
                "\n" +
                "Best Regards,\n" +
                "\n" +
                "Yuliya Pliavaka\n" +
                "Mob. +91-7720825835 <+91%2077208%2025835> / Linkedin\n" +
                "www.hardwear.io  Hardware Security Conference\n" +
                "www.nullcon.net  Nullcon Information Security Conference\n" +
                "www.payatu.com   Payatu Technologies\n");
        nonValid.setSubject("hardwear.io CFP is Open & New Security Training in Berlin!");
        nonValid.setTo("fulldisclosure@seclists.org");

        fdEmail.setContent("SEC Consult Vulnerability Lab Security Advisory < 20180314-0 >\n" +
                "=======================================================================\n" +
                "              title: Arbitrary Shortcode Execution & Local File Inclusion\n" +
                "            product: WOOF - WooCommerce Products Filter (PluginUs.Net)\n" +
                " vulnerable version: 1.1.9\n" +
                "      fixed version: 2.2.0\n" +
                "         CVE number: (requested but not yet received)\n" +
                "             impact: Critical\n" +
                "           homepage: https://pluginus.net/\n" +
                "              found: 2018-02-20\n" +
                "                 by: Ahmad Ramadhan Amizudin (Office Kuala Lumpur)\n" +
                "                     SEC Consult Vulnerability Lab\n" +
                "\n" +
                "                     An integrated part of SEC Consult\n" +
                "                     Europe | Asia | North America\n" +
                "\n" +
                "                     https://www.sec-consult.com\n" +
                "\n" +
                "=======================================================================\n" +
                "\n" +
                "Vendor description:\n" +
                "-------------------\n" +
                "\"PluginUs.Net is a little team of talented professionals from Ukraine. Unlike\n" +
                "most of the big companies on the net, we believe in individual approach to\n" +
                "every our customer. Web development is our passion and we always try to go an\n" +
                "extra mile over our clients' expectations.\n" +
                "\n" +
                "Our team specializes in development of WordPress plugins. It's always exciting\n" +
                "to try new technologies and approaches to get the project done and impress\n" +
                "clients by realization of their ideas!\"\n" +
                "\n" +
                "Source: https://pluginus.net/about-us/\n" +
                "\n" +
                "\n" +
                "Business recommendation:\n" +
                "------------------------\n" +
                "SEC Consult recommends to ugprade to the latest version available\n" +
                "as soon as possible. Further detailed security tests should be performed\n" +
                "in order to identify potential other security issues.\n" +
                "\n" +
                "\n" +
                "Vulnerability overview/description:\n" +
                "-----------------------------------\n" +
                "1. Arbitrary Shortcode Execution\n" +
                "The plugin implemented a page redraw AJAX function accessible to anyone\n" +
                "without any authentication.\n" +
                "\n" +
                "WordPress shortcode markup in the \"shortcode\" parameters would be evaluated.\n" +
                "Normally unauthenticated users can't evaluate shortcodes as they are often\n" +
                "sensitive.\n" +
                "\n" +
                "Additionally, it is noted that there are other implemented shortcodes that are\n" +
                "being used in this plugin which can be abused through the same attack. Worst,\n" +
                "some of them could lead to remote code execution.\n" +
                "\n" +
                "\n" +
                "2. Local File Inclusion\n" +
                "The vulnerability is due to the lack of args/input validation on render_html\n" +
                "before allowing it to be called by extract(), a PHP built-in function. Because\n" +
                "of this, the supplied args/input can be used to overwrite the $pagepath\n" +
                "variable which then could lead to local file inclusion attack.\n" +
                "\n" +
                "\n" +
                "Proof of concept:\n" +
                "-----------------\n" +
                "1. Arbitrary Shortcode Execution\n" +
                "The parameter \"shortcode\" within the \"admin-ajax.php\" script is affected by\n" +
                "the code execution vulnerability:\n" +
                "\n" +
                "POST /wp-admin/admin-ajax.php HTTP/1.1\n" +
                "[...]\n" +
                "\n" +
                "action=woof_redraw_woof&shortcode=<<shortcode without []>>\n" +
                "\n" +
                "\n" +
                "2. Local File Inclusion\n" +
                "The parameter \"shortcode\" within the \"admin-ajax.php\" script is affected by\n" +
                "the local file inclusion vulnerability:\n" +
                "\n" +
                "POST /wp-admin/admin-ajax.php HTTP/1.1\n" +
                "[...]\n" +
                "\n" +
                "action=woof_redraw_woof&shortcode=woof_search_options pagepath=/etc/passwd\n" +
                "\n" +
                "\n" +
                "Vulnerable / tested versions:\n" +
                "-----------------------------\n" +
                "PluginUs.Net WooCommerce Products Filter version 1.1.9 has been tested and\n" +
                "found to be vulnerable.\n" +
                "\n" +
                "\n" +
                "Vendor contact timeline:\n" +
                "------------------------\n" +
                "2018-02-20: Contacting vendor through realmag777@gmail.com\n" +
                "2018-02-20: Vendor agreed to proceed without encrypted channel\n" +
                "2018-02-21: Sent security advisory to vendor\n" +
                "2018-02-26: Vendor sent patch containing the fixes\n" +
                "2018-02-26: Informed vendor the patch doesn't fully mitigate the vulnerability\n" +
                "2018-03-12: Request update from vendor\n" +
                "2018-03-12: Vendor said they already published the patch\n" +
                "2018-03-14: Public release of security advisory\n" +
                "\n" +
                "\n" +
                "Solution:\n" +
                "---------\n" +
                "The vendor provides an updated version and users are urged to upgrade to version\n" +
                "2.2.0 immediately:\n" +
                "\n" +
                "https://www.woocommerce-filter.com/update-woocommerce-products-filter-v-2-2-0/\n" +
                "\n" +
                "\n" +
                "Workaround:\n" +
                "-----------\n" +
                "None\n" +
                "\n" +
                "\n" +
                "Advisory URL:\n" +
                "-------------\n" +
                "https://www.sec-consult.com/en/vulnerability-lab/advisories/index.html\n" +
                "\n" +
                "\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "\n" +
                "SEC Consult Vulnerability Lab\n" +
                "\n" +
                "SEC Consult\n" +
                "Europe | Asia | North America\n" +
                "\n" +
                "About SEC Consult Vulnerability Lab\n" +
                "The SEC Consult Vulnerability Lab is an integrated part of SEC Consult. It\n" +
                "ensures the continued knowledge gain of SEC Consult in the field of network\n" +
                "and application security to stay ahead of the attacker. The SEC Consult\n" +
                "Vulnerability Lab supports high-quality penetration testing and the evaluation\n" +
                "of new offensive and defensive technologies for our customers. Hence our\n" +
                "customers obtain the most current information about vulnerabilities and valid\n" +
                "recommendation about the risk profile of new technologies.\n" +
                "\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "Interested to work with the experts of SEC Consult?\n" +
                "Send us your application https://www.sec-consult.com/en/career/index.html\n" +
                "\n" +
                "Interested in improving your cyber security with the experts of SEC Consult?\n" +
                "Contact our local offices https://www.sec-consult.com/en/contact/index.html\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "\n" +
                "Mail: research at sec-consult dot com\n" +
                "Web: https://www.sec-consult.com\n" +
                "Blog: http://blog.sec-consult.com\n" +
                "Twitter: https://twitter.com/sec_consult\n" +
                "\n" +
                "EOF Ahmad Ramadhan / @2018\n" +
                "\n" +
                "\n" +
                "_______________________________________________\n" +
                "Sent through the Full Disclosure mailing list\n" +
                "https://nmap.org/mailman/listinfo/fulldisclosure\n" +
                "Web Archives & RSS: http://seclists.org/fulldisclosure/");

        fdEmail.setTo("fulldisclosure@seclists.org");
        fdEmail.setSubject("[FD] SEC Consult SA-20180314-0 :: Arbitrary Shortcode Execution & Local File Inclusion in WooCommerce Products Filter (PluginUs.Net)");

        vmWareEmail.setContent("-----BEGIN PGP SIGNED MESSAGE-----\n" +
                "Hash: SHA1\n" +
                "\n" +
                "- -----------------------------------------------------------------------\n" +
                "\n" +
                "                               VMware Security Advisory\n" +
                "\n" +
                "Advisory ID: VMSA-2017-0015.1\n" +
                "Severity:    Critical\n" +
                "Synopsis:    VMware ESXi, vCenter Server, Fusion & Workstation updates \n" +
                "             resolve multiple security vulnerabilities\n" +
                "Issue date:  2017-09-14 \n" +
                "Updated on:  2017-09-15\n" +
                "CVE number:  CVE-2017-4924, CVE-2017-4925, CVE-2017-4926\n" +
                "\n" +
                "1. Summary\n" +
                "\n" +
                "   VMware ESXi, vCenter Server, Fusion and Workstation updates resolve \n" +
                "   multiple security vulnerabilities.\n" +
                "   \n" +
                "2. Relevant Products\n" +
                "    \n" +
                "   VMware ESXi (ESXi)      \n" +
                "   VMware vCenter Server\n" +
                "   VMware Fusion Pro / Fusion (Fusion)   \n" +
                "   VMware Workstation Pro / Player (Workstation)\n" +
                "\n" +
                "3. Problem Description\n" +
                "\n" +
                "   a. Out-of-bounds write vulnerability in SVGA\n" +
                "   \n" +
                "   VMware ESXi, Workstation & Fusion contain an out-of-bounds write \n" +
                "   vulnerability in SVGA device. This issue may allow a guest to \n" +
                "   execute code on the host.\n" +
                "   \n" +
                "   VMware would like to thank Nico Golde and Ralf-Philipp Weinmann of\n" +
                "   Comsecuris UG (haftungsbeschraenkt) working with ZDI for reporting \n" +
                "   this issue to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has\n" +
                "   assigned the identifier CVE-2017-4924 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "\n" +
                "   VMware      Product Running           Replace with/       Mitigation\n" +
                "   Product     Version on      Severity  Apply patch         Workaround\n" +
                "   =========== ======= ======= ========  =============       ==========\n" +
                "      ESXi      6.5     ESXi   Critical ESXi650-201707101-SG   None\n" +
                "      ESXi      6.0     ESXi    N/A       Not affected          N/A\n" +
                "      ESXi      5.5     ESXi    N/A       Not affected          N/A\n" +
                "   Workstation  12.x    Any    Critical    12.5.7              None\n" +
                "     Fusion     8.x     OS X   Critical    8.5.8               None  \n" +
                "\n" +
                "   b. Guest RPC NULL pointer dereference vulnerability  \n" +
                "   \n" +
                "   VMware ESXi, Workstation & Fusion contain a NULL pointer dereference\n" +
                "   vulnerability. This issue occurs when handling guest RPC requests.\n" +
                "   Successful exploitation of this issue may allow attackers with \n" +
                "   normal user privileges to crash their VMs.\n" +
                "   \n" +
                "   VMware would like to thank Zhang Haitao for reporting this issue \n" +
                "   to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has \n" +
                "   assigned the identifier CVE-2017-4925 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "\n" +
                "   VMware      Product Running          Replace with/        Mitigation\n" +
                "   Product     Version on      Severity Apply patch          Workaround\n" +
                "   =========== ======= ======= ======== =============        ==========\n" +
                "      ESXi      6.5     ESXi   Moderate ESXi650-201707101-SG   None\n" +
                "      ESXi      6.0     ESXi   Moderate ESXi600-201706101-SG   None\n" +
                "      ESXi      5.5     ESXi   Moderate ESXi550-201709101-SG   None\n" +
                "   Workstation  12.x    Any    Moderate    12.5.3              None \n" +
                "     Fusion     8.x     OS X   Moderate     8.5.4              None\n" +
                "   \n" +
                "   c. Stored XSS in H5 Client\n" +
                "   \n" +
                "   vCenter Server H5 Client contains a vulnerability that may allow for \n" +
                "   stored cross-site scripting (XSS). An attacker with VC user \n" +
                "   privileges can inject malicious java-scripts which will get executed\n" +
                "   when other VC users access the page.\n" +
                "   \n" +
                "   VMware would like to thank Thomas Ornetzeder for reporting this \n" +
                "   issue to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has \n" +
                "   assigned the identifier CVE-2017-4926 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "   \n" +
                "   VMware          Product Running           Replace with/   Mitigation\n" +
                "   Product         Version on      Severity  Apply patch     Workaround\n" +
                "   ==============  ======= ======= ========  =============   ==========\n" +
                "   vCenter Server   6.5    Windows Moderate     6.5 U1          None\n" +
                "   vCenter Server   6.0    Windows   N/A      Not affected      N/A\n" +
                "   vCenter Server   5.5    Windows   N/A      Not affected      N/A\n" +
                "   \n" +
                "4. Solution\n" +
                "\n" +
                "   Please review the patch/release notes for your product and\n" +
                "   version and verify the checksum of your downloaded file.\n" +
                "   \n" +
                "   ESXi 6.5  \n" +
                "   -------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2149933 \n" +
                "   \n" +
                "   ESXi 6.0  \n" +
                "   -------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2149960  \n" +
                "   \n" +
                "   ESXi 5.5 \n" +
                "   ------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2150876\n" +
                "   \n" +
                "   VMware vCenter Server 6.5 U1\n" +
                "   Downloads:\n" +
                "   https://my.vmware.com/web/vmware/details?downloadGroup=VC65U1\n" +
                "   &productId=614&rPId=17343\n" +
                "   Documentation:\n" +
                "   https://docs.vmware.com/en/VMware-vSphere/index.html\n" +
                "   \n" +
                "   VMware Workstation Pro 12.5.7 \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadworkstation  \n" +
                "   https://www.vmware.com/support/pubs/ws_pubs.html  \n" +
                "    \n" +
                "   VMware Workstation Player 12.5.7  \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadplayer  \n" +
                "   https://www.vmware.com/support/pubs/player_pubs.html \n" +
                "   \n" +
                "   VMware Workstation Pro 12.5.3  \n" +
                "   Downloads and Documentation:\n" +
                "   https://www.vmware.com/go/downloadworkstation\n" +
                "   https://www.vmware.com/support/pubs/ws_pubs.html   \n" +
                " \n" +
                "   VMware Workstation Player 12.5.3    \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadplayer\n" +
                "   https://www.vmware.com/support/pubs/player_pubs.html\n" +
                "   \n" +
                "   VMware Fusion Pro / Fusion 8.5.8\n" +
                "   Downloads and Documentation\n" +
                "   https://www.vmware.com/go/downloadfusion\n" +
                "   https://www.vmware.com/support/pubs/fusion_pubs.html\n" +
                "   \n" +
                "   VMware Fusion Pro / Fusion 8.5.4\n" +
                "   Downloads and Documentation\n" +
                "   https://www.vmware.com/go/downloadfusion\n" +
                "   https://www.vmware.com/support/pubs/fusion_pubs.html\n" +
                "   \n" +
                "   \n" +
                "5. References\n" +
                "\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4924\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4925\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4926\n" +
                "      \n" +
                "- ------------------------------------------------------------------------\n" +
                "\n" +
                "6. Change log\n" +
                "\n" +
                "   2017-09-14 VMSA-2017-0015\n" +
                "   Initial security advisory in conjunction with the release of VMware\n" +
                "   ESXi 5.5 patches on 2017-09-14\n" +
                "   \n" +
                "   2017-09-15 VMSA-2017-0015.1 Corrected the underlying component \n" +
                "   affected from SVGA driver to device.\n" +
                "\n" +
                "- ------------------------------------------------------------------------\n" +
                "7. Contact\n" +
                "\n" +
                "   E-mail list for product security notifications and announcements:\n" +
                "   http://lists.vmware.com/cgi-bin/mailman/listinfo/security-announce\n" +
                "\n" +
                "   This Security Advisory is posted to the following lists:\n" +
                "   \n" +
                "     security-announce@lists.vmware.com\n" +
                "     bugtraq@securityfocus.com\n" +
                "     fulldisclosure@seclists.org\n" +
                "\n" +
                "   E-mail: security@vmware.com\n" +
                "   PGP key at: https://kb.vmware.com/kb/1055\n" +
                "\n" +
                "   VMware Security Advisories\n" +
                "   http://www.vmware.com/security/advisories\n" +
                "\n" +
                "   VMware Security Response Policy\n" +
                "   https://www.vmware.com/support/policies/security_response.html\n" +
                "\n" +
                "   VMware Lifecycle Support Phases\n" +
                "   https://www.vmware.com/support/policies/lifecycle.html\n" +
                "   \n" +
                "   VMware Security & Compliance Blog\n" +
                "   https://blogs.vmware.com/security\n" +
                "\n" +
                "   Twitter\n" +
                "   https://twitter.com/VMwareSRC\n" +
                "\n" +
                "   Copyright 2017 VMware Inc.  All rights reserved.\n" +
                "\n" +
                "-----BEGIN PGP SIGNATURE-----\n" +
                "Version: Encryption Desktop 10.4.1 (Build 490)\n" +
                "Charset: utf-8\n" +
                "\n" +
                "wj8DBQFZu8TPDEcm8Vbi9kMRAtcMAKC6idpn3c7336BWJfAsUm/f+dwyKwCdH4X8\n" +
                "hecXRicDNT6WR6AGFpWeSuI=\n" +
                "=4fr+\n" +
                "-----END PGP SIGNATURE-----\n");

        vmWareEmail.setTo("security-announce@lists.vmware.com");
        vmWareEmail.setSubject("[Security-announce] Updated VMSA-2017-0015.1 - VMware ESXi, vCenter Server, Fusion & Workstation updates resolve multiple security vulnerabilities");

        getVmWareEmailDup.setContent("-----BEGIN PGP SIGNED MESSAGE-----\n" +
                "Hash: SHA1\n" +
                "\n" +
                "- -----------------------------------------------------------------------\n" +
                "\n" +
                "                               VMware Security Advisory\n" +
                "\n" +
                "Synopsis:    VMware ESXi, vCenter Server, Fusion & Workstation updates \n" +
                "             resolve multiple security vulnerabilities\n" +
                "Issue date:  2017-09-14 \n" +
                "Updated on:  2017-09-15\n" +
                "CVE number:  CVE-2017-4924, CVE-2017-4925, CVE-2017-4926\n" +
                "\n" +
                "1. Summary\n" +
                "\n" +
                "   VMware ESXi, vCenter Server, Fusion and Workstation updates resolve \n" +
                "   multiple security vulnerabilities.\n" +
                "   \n" +
                "2. Relevant Products\n" +
                "    \n" +
                "   VMware ESXi (ESXi)      \n" +
                "   VMware vCenter Server\n" +
                "   VMware Fusion Pro / Fusion (Fusion)   \n" +
                "   VMware Workstation Pro / Player (Workstation)\n" +
                "\n" +
                "3. Problem Description\n" +
                "\n" +
                "   a. Out-of-bounds write vulnerability in SVGA\n" +
                "   \n" +
                "   VMware ESXi, Workstation & Fusion contain an out-of-bounds write \n" +
                "   vulnerability in SVGA device. This issue may allow a guest to \n" +
                "   execute code on the host.\n" +
                "   \n" +
                "   VMware would like to thank Nico Golde and Ralf-Philipp Weinmann of\n" +
                "   Comsecuris UG (haftungsbeschraenkt) working with ZDI for reporting \n" +
                "   this issue to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has\n" +
                "   assigned the identifier CVE-2017-4924 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "\n" +
                "   VMware      Product Running           Replace with/       Mitigation\n" +
                "   Product     Version on      Severity  Apply patch         Workaround\n" +
                "   =========== ======= ======= ========  =============       ==========\n" +
                "      ESXi      6.5     ESXi   Critical ESXi650-201707101-SG   None\n" +
                "      ESXi      6.0     ESXi    N/A       Not affected          N/A\n" +
                "      ESXi      5.5     ESXi    N/A       Not affected          N/A\n" +
                "   Workstation  12.x    Any    Critical    12.5.7              None\n" +
                "     Fusion     8.x     OS X   Critical    8.5.8               None  \n" +
                "\n" +
                "   b. Guest RPC NULL pointer dereference vulnerability  \n" +
                "   \n" +
                "   VMware ESXi, Workstation & Fusion contain a NULL pointer dereference\n" +
                "   vulnerability. This issue occurs when handling guest RPC requests.\n" +
                "   Successful exploitation of this issue may allow attackers with \n" +
                "   normal user privileges to crash their VMs.\n" +
                "   \n" +
                "   VMware would like to thank Zhang Haitao for reporting this issue \n" +
                "   to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has \n" +
                "   assigned the identifier CVE-2017-4925 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "\n" +
                "   VMware      Product Running          Replace with/        Mitigation\n" +
                "   Product     Version on      Severity Apply patch          Workaround\n" +
                "   =========== ======= ======= ======== =============        ==========\n" +
                "      ESXi      6.5     ESXi   Moderate ESXi650-201707101-SG   None\n" +
                "      ESXi      6.0     ESXi   Moderate ESXi600-201706101-SG   None\n" +
                "      ESXi      5.5     ESXi   Moderate ESXi550-201709101-SG   None\n" +
                "   Workstation  12.x    Any    Moderate    12.5.3              None \n" +
                "     Fusion     8.x     OS X   Moderate     8.5.4              None\n" +
                "   \n" +
                "   c. Stored XSS in H5 Client\n" +
                "   \n" +
                "   vCenter Server H5 Client contains a vulnerability that may allow for \n" +
                "   stored cross-site scripting (XSS). An attacker with VC user \n" +
                "   privileges can inject malicious java-scripts which will get executed\n" +
                "   when other VC users access the page.\n" +
                "   \n" +
                "   VMware would like to thank Thomas Ornetzeder for reporting this \n" +
                "   issue to us.\n" +
                "   \n" +
                "   The Common Vulnerabilities and Exposures project (cve.mitre.org) has \n" +
                "   assigned the identifier CVE-2017-4926 to this issue.\n" +
                "\n" +
                "   Column 5 of the following table lists the action required to\n" +
                "   remediate the vulnerability in each release, if a solution is\n" +
                "   available.\n" +
                "   \n" +
                "   VMware          Product Running           Replace with/   Mitigation\n" +
                "   Product         Version on      Severity  Apply patch     Workaround\n" +
                "   ==============  ======= ======= ========  =============   ==========\n" +
                "   vCenter Server   6.5    Windows Moderate     6.5 U1          None\n" +
                "   vCenter Server   6.0    Windows   N/A      Not affected      N/A\n" +
                "   vCenter Server   5.5    Windows   N/A      Not affected      N/A\n" +
                "   \n" +
                "4. Solution\n" +
                "\n" +
                "   Please review the patch/release notes for your product and\n" +
                "   version and verify the checksum of your downloaded file.\n" +
                "   \n" +
                "   ESXi 6.5  \n" +
                "   -------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2149933 \n" +
                "   \n" +
                "   ESXi 6.0  \n" +
                "   -------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2149960  \n" +
                "   \n" +
                "   ESXi 5.5 \n" +
                "   ------------\n" +
                "   Downloads:  \n" +
                "   https://www.vmware.com/patchmgr/findPatch.portal     \n" +
                "   Documentation:  \n" +
                "   http://kb.vmware.com/kb/2150876\n" +
                "   \n" +
                "   VMware vCenter Server 6.5 U1\n" +
                "   Downloads:\n" +
                "   https://my.vmware.com/web/vmware/details?downloadGroup=VC65U1\n" +
                "   &productId=614&rPId=17343\n" +
                "   Documentation:\n" +
                "   https://docs.vmware.com/en/VMware-vSphere/index.html\n" +
                "   \n" +
                "   VMware Workstation Pro 12.5.7 \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadworkstation  \n" +
                "   https://www.vmware.com/support/pubs/ws_pubs.html  \n" +
                "    \n" +
                "   VMware Workstation Player 12.5.7  \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadplayer  \n" +
                "   https://www.vmware.com/support/pubs/player_pubs.html \n" +
                "   \n" +
                "   VMware Workstation Pro 12.5.3  \n" +
                "   Downloads and Documentation:\n" +
                "   https://www.vmware.com/go/downloadworkstation\n" +
                "   https://www.vmware.com/support/pubs/ws_pubs.html   \n" +
                " \n" +
                "   VMware Workstation Player 12.5.3    \n" +
                "   Downloads and Documentation:  \n" +
                "   https://www.vmware.com/go/downloadplayer\n" +
                "   https://www.vmware.com/support/pubs/player_pubs.html\n" +
                "   \n" +
                "   VMware Fusion Pro / Fusion 8.5.8\n" +
                "   Downloads and Documentation\n" +
                "   https://www.vmware.com/go/downloadfusion\n" +
                "   https://www.vmware.com/support/pubs/fusion_pubs.html\n" +
                "   \n" +
                "   VMware Fusion Pro / Fusion 8.5.4\n" +
                "   Downloads and Documentation\n" +
                "   https://www.vmware.com/go/downloadfusion\n" +
                "   https://www.vmware.com/support/pubs/fusion_pubs.html\n" +
                "   \n" +
                "   \n" +
                "5. References\n" +
                "\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4924\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4925\n" +
                "   http://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2017-4926\n" +
                "      \n" +
                "- ------------------------------------------------------------------------\n" +
                "\n" +
                "6. Change log\n" +
                "\n" +
                "   2017-09-14 VMSA-2017-0015\n" +
                "   Initial security advisory in conjunction with the release of VMware\n" +
                "   ESXi 5.5 patches on 2017-09-14\n" +
                "   \n" +
                "   2017-09-15 VMSA-2017-0015.1 Corrected the underlying component \n" +
                "   affected from SVGA driver to device.\n" +
                "\n" +
                "- ------------------------------------------------------------------------\n" +
                "7. Contact\n" +
                "\n" +
                "   E-mail list for product security notifications and announcements:\n" +
                "   http://lists.vmware.com/cgi-bin/mailman/listinfo/security-announce\n" +
                "\n" +
                "   This Security Advisory is posted to the following lists:\n" +
                "   \n" +
                "     security-announce@lists.vmware.com\n" +
                "     bugtraq@securityfocus.com\n" +
                "     fulldisclosure@seclists.org\n" +
                "\n" +
                "   E-mail: security@vmware.com\n" +
                "   PGP key at: https://kb.vmware.com/kb/1055\n" +
                "\n" +
                "   VMware Security Advisories\n" +
                "   http://www.vmware.com/security/advisories\n" +
                "\n" +
                "   VMware Security Response Policy\n" +
                "   https://www.vmware.com/support/policies/security_response.html\n" +
                "\n" +
                "   VMware Lifecycle Support Phases\n" +
                "   https://www.vmware.com/support/policies/lifecycle.html\n" +
                "   \n" +
                "   VMware Security & Compliance Blog\n" +
                "   https://blogs.vmware.com/security\n" +
                "\n" +
                "   Twitter\n" +
                "   https://twitter.com/VMwareSRC\n" +
                "\n" +
                "   Copyright 2017 VMware Inc.  All rights reserved.\n" +
                "\n" +
                "-----BEGIN PGP SIGNATURE-----\n" +
                "Version: Encryption Desktop 10.4.1 (Build 490)\n" +
                "Charset: utf-8\n" +
                "\n" +
                "wj8DBQFZu8TPDEcm8Vbi9kMRAtcMAKC6idpn3c7336BWJfAsUm/f+dwyKwCdH4X8\n" +
                "hecXRicDNT6WR6AGFpWeSuI=\n" +
                "=4fr+\n" +
                "-----END PGP SIGNATURE-----\n");

        getVmWareEmailDup.setTo("security-announce@lists.vmware.com");
        getVmWareEmailDup.setSubject("[Security-announce] Updated VMSA-2017-0015.1 - VMware ESXi, vCenter Server, Fusion & Workstation updates resolve multiple security vulnerabilities");



        debianEmail.setContent("-----BEGIN PGP SIGNED MESSAGE-----\n" +
                "Hash: SHA512\n" +
                "\n" +
                "- -------------------------------------------------------------------------\n" +
                "Debian Security Advisory DSA-4141-1                   security@debian.org\n" +
                "https://www.debian.org/security/                     Salvatore Bonaccorso\n" +
                "March 16, 2018                        https://www.debian.org/security/faq\n" +
                "- -------------------------------------------------------------------------\n" +
                "\n" +
                "Package        : libvorbisidec\n" +
                "CVE ID         : CVE-2018-5147\n" +
                "Debian Bug     : 893132\n" +
                "\n" +
                "Huzaifa Sidhpurwala discovered that an out-of-bounds memory write in the\n" +
                "codebook parsing code of the Libtremor multimedia library could result\n" +
                "in the execution of arbitrary code if a malformed Vorbis file is opened.\n" +
                "\n" +
                "For the oldstable distribution (jessie), this problem has been fixed\n" +
                "in version 1.0.2+svn18153-1~deb8u2.\n" +
                "\n" +
                "For the stable distribution (stretch), this problem has been fixed in\n" +
                "version 1.0.2+svn18153-1+deb9u1.\n" +
                "\n" +
                "We recommend that you upgrade your libvorbisidec packages.\n" +
                "\n" +
                "For the detailed security status of libvorbisidec please refer to its\n" +
                "security tracker page at:\n" +
                "https://security-tracker.debian.org/tracker/libvorbisidec\n" +
                "\n" +
                "Further information about Debian Security Advisories, how to apply\n" +
                "these updates to your system and frequently asked questions can be\n" +
                "found at: https://www.debian.org/security/\n" +
                "\n" +
                "Mailing list: debian-security-announce@lists.debian.org\n" +
                "-----BEGIN PGP SIGNATURE-----\n" +
                "\n" +
                "iQKTBAEBCgB9FiEERkRAmAjBceBVMd3uBUy48xNDz0QFAlqsMoZfFIAAAAAALgAo\n" +
                "aXNzdWVyLWZwckBub3RhdGlvbnMub3BlbnBncC5maWZ0aGhvcnNlbWFuLm5ldDQ2\n" +
                "NDQ0MDk4MDhDMTcxRTA1NTMxRERFRTA1NENCOEYzMTM0M0NGNDQACgkQBUy48xND\n" +
                "z0RusxAAky9XYo+9XeZK8rUyu91/MFSvOwCxgHahp9DVQ7mTc2W8RTLAW/NDN2Rg\n" +
                "HOGb9Mz//l631kmK5pxk778WRcRPxD8F7M1BuR726onh1WsvrMMFrYqaSyN+9rtO\n" +
                "Q2CoF3SD5GcyzOLe25+HudW32hIH3Qh0m18aPQo6Bl7QVluxg0Sk/OHArccMlE9t\n" +
                "/N2Z+5WccjDPZW/ZDJXlaKflkXf66Npe9QZGY45VdBFygz24pqw1NV3Hpl4U93cw\n" +
                "rCywm/9UnGti1s4yRCr/55Lil8Afnm5cj2HfibHqcpBfpMGY98sKfY3N03YE/ZO2\n" +
                "4tHwxqI1o/8SoktkcXrltnqd0eYGGR0CDPccJ6yoFAjfMX6WNSTJwauWMZZ0yDko\n" +
                "GRQv/ZhKVTvmEDgPTbJD3xflKmO5UDcgbLOq8MjdoBUOvbYgrkksrERodnzqRYcO\n" +
                "8/NXw+a0dmUcEnqtcBAQKqHejGlibsFsKlKFIUR8kos5efXaI3+6aLHJmahTwlW+\n" +
                "SOc7amh9xEa0eF/MKSSl9bGBNMMSJlnIarIe+pwurdeDPLECvM1XieJZYU9ue5v0\n" +
                "yrlZS3t9nmCdtyp/6yHbAQ65I4rMlnn0s2utfH3/15KadGvxuPLyROVeY/ZWMTor\n" +
                "HmPLHlACNYRU/b+/9IDRu47IcgPI3iXkJCFCeAPKWHdAb/CEkAI=\n" +
                "=hYbe\n" +
                "-----END PGP SIGNATURE-----");

        debianEmail.setSubject("[SECURITY] [DSA 4141-1] libvorbisidec security update");
        debianEmail.setTo("debian-security-announce@lists.debian.org");


        /*Setting US Cert Alert Up*/
        usCertAlert.setOrigin("US-CERT Alerts");
        usCertAlert.setTitle("TA17-318B: HIDDEN COBRA – North Korean Trojan: Volgmer");
        usCertAlert.setLink("https://www.us-cert.gov/ncas/alerts/TA17-318B");
        usCertAlert.setDescription("Original release date: November 14, 2017 | Last revised: November 22, 2017<br />\n" +
                "\t\t<h3>Systems Affected</h3>\n" +
                "\t\t<p>Network systems</p>\t\t\n" +
                "\t\t<h3>Overview</h3>\n" +
                "\t\t<p>This joint Technical Alert (TA) is the result of analytic efforts between the Department of Homeland Security (DHS) and the Federal Bureau of Investigation (FBI). Working with U.S. government partners, DHS and FBI identified Internet Protocol (IP) addresses and other indicators of compromise (IOCs) associated with a Trojan malware variant used by the North Korean government—commonly known as Volgmer. The U.S. Government refers to malicious cyber activity by the North Korean government as HIDDEN COBRA. For more information on HIDDEN COBRA activity, visit <a href=\"https://www.us-cert.gov/hiddencobra\">https://www.us-cert.gov/hiddencobra</a>.</p><p>FBI has high confidence that HIDDEN COBRA actors are using the IP addresses—listed in this report’s IOC files—to maintain a presence on victims’ networks and to further network exploitation. DHS and FBI are distributing these IP addresses to enable network defense and reduce exposure to North Korean government malicious cyber activity.</p><p>This alert includes IOCs related to HIDDEN COBRA, IP addresses linked to systems infected with Volgmer malware, malware descriptions, and associated signatures. This alert also includes suggested response actions to the IOCs provided, recommended mitigation techniques, and information on reporting incidents. If users or administrators detect activity associated with the Volgmer malware, they should immediately flag it, report it to the DHS National Cybersecurity and Communications Integration Center (NCCIC) or the FBI Cyber Watch (CyWatch), and give it the highest priority for enhanced mitigation.</p><p>For a downloadable copy of IOCs, see:</p><ul><li>IOCs (<a href=\"https://www.us-cert.gov/sites/default/files/publications/TA-17-318B-IOCs.csv\">.csv</a>)</li><li>IOCs (<a href=\"https://www.us-cert.gov/sites/default/files/publications/TA-17-318B-IOCs.xml\">.stix</a>)</li></ul><p>NCCIC conducted analysis on five files associated with or identified as Volgmer malware and produced a Malware Analysis Report (MAR). MAR-10135536-D examines the tactics, techniques, and procedures observed. For a downloadable copy of the MAR, see:</p><ul><li>MAR (<a href=\"https://www.us-cert.gov/sites/default/files/publications/MAR-10135536-D_WHITE_S508C.PDF\">.pdf</a>)</li><li>MAR IOCs (<a href=\"https://www.us-cert.gov/sites/default/files/publications/MAR-10135536-D_WHITE_stix.xml\">.stix</a>)</li></ul>\t\t\n" +
                "\t\t<h3>Description</h3>\n" +
                "\t\t<p>Volgmer is a backdoor Trojan designed to provide covert access to a compromised system. Since at least 2013, HIDDEN COBRA actors have been observed using Volgmer malware in the wild to target the government, financial, automotive, and media industries.</p><p>It is suspected that spear phishing is the primary delivery mechanism for Volgmer infections; however, HIDDEN COBRA actors use a suite of custom tools, some of which could also be used to initially compromise a system. Therefore, it is possible that additional HIDDEN COBRA malware may be present on network infrastructure compromised with Volgmer</p><p>The U.S. Government has analyzed Volgmer’s infrastructure and have identified it on systems using both dynamic and static IP addresses. At least 94 static IP addresses were identified, as well as dynamic IP addresses registered across various countries. The greatest concentrations of dynamic IPs addresses are identified below by approximate percentage:</p><ul><li>India (772 IPs) 25.4 percent</li><li>Iran (373 IPs) 12.3 percent</li><li>Pakistan (343 IPs) 11.3 percent</li><li>Saudi Arabia (182 IPs) 6 percent</li><li>Taiwan (169 IPs) 5.6 percent</li><li>Thailand (140 IPs) 4.6 percent</li><li>Sri Lanka (121 IPs) 4 percent</li><li>China (82 IPs, including Hong Kong (12)) 2.7 percent</li><li>Vietnam (80 IPs) 2.6 percent</li><li>Indonesia (68 IPs) 2.2 percent</li><li>Russia (68 IPs) 2.2 percent</li></ul><h3><strong>Technical Details</strong></h3><p>As a backdoor Trojan, Volgmer has several capabilities including: gathering system information, updating service registry keys, downloading and uploading files, executing commands, terminating processes, and listing directories. In one of the samples received for analysis, the US-CERT Code Analysis Team observed botnet controller functionality.</p><p>Volgmer payloads have been observed in 32-bit form as either executables or dynamic-link library (.dll) files. The malware uses a custom binary protocol to beacon back to the command and control (C2) server, often via TCP port 8080 or 8088, with some payloads implementing Secure Socket Layer (SSL) encryption to obfuscate communications.</p><p>Malicious actors commonly maintain persistence on a victim’s system by installing the malware-as-a-service. Volgmer queries the system and randomly selects a service in which to install a copy of itself. The malware then overwrites the ServiceDLL entry in the selected service's registry entry. In some cases, HIDDEN COBRA actors give the created service a pseudo-random name that may be composed of various hardcoded words.</p><h3><strong>Detection and Response</strong></h3><p>This alert’s IOC files provide HIDDEN COBRA indicators related to Volgmer. DHS and FBI recommend that network administrators review the information provided, identify whether any of the provided IP addresses fall within their organizations’ allocated IP address space, and—if found—take necessary measures to remove the malware.</p><p>When reviewing network perimeter logs for the IP addresses, organizations may find instances of these IP addresses attempting to connect to their systems. Upon reviewing the traffic from these IP addresses, system owners may find some traffic relates to malicious activity and some traffic relates to legitimate activity.</p><h3>Network Signatures and Host-Based Rules</h3><p>This section contains network signatures and host-based rules that can be used to detect malicious activity associated with HIDDEN COBRA actors. Although created using a comprehensive vetting process, the possibility of false positives always remains. These signatures and rules should be used to supplement analysis and should not be used as a sole source of attributing this activity to HIDDEN COBRA actors.</p><h4>Network Signatures</h4><p><code>alert tcp any any -&#62; any any (msg:\"Malformed_UA\"; content:\"User-Agent: Mozillar/\"; depth:500; sid:99999999;)</code></p><p>___________________________________________________________________________________________________</p><h4>YARA Rules</h4><p><code>rule volgmer<br />{<br />meta:<br />    description = \"Malformed User Agent\"<br />strings:<br />    $s = \"Mozillar/\"<br />condition:<br />    (uint16(0) == 0x5A4D and uint16(uint32(0x3c)) == 0x4550) and $s<br />}</code></p>\t\t\n" +
                "\t\t<h3>Impact</h3>\n" +
                "\t\t<p>A successful network intrusion can have severe impacts, particularly if the compromise becomes public and sensitive information is exposed. Possible impacts include</p><ul><li>temporary or permanent loss of sensitive or proprietary information,</li><li>disruption to regular operations,</li><li>financial losses incurred to restore systems and files, and</li><li>potential harm to an organization’s reputation.</li></ul>\t\t\n" +
                "\t\t<h3>Solution</h3>\n" +
                "\t\t<h4><strong><em>Mitigation Strategies</em></strong></h4><p>DHS recommends that users and administrators use the following best practices as preventive measures to protect their computer networks:</p><ul><li>Use application whitelisting to help prevent malicious software and unapproved programs from running. Application whitelisting is one of the best security strategies as it allows only specified programs to run, while blocking all others, including malicious software.</li><li>Keep operating systems and software up-to-date with the latest patches. Vulnerable applications and operating systems are the target of most attacks. Patching with the latest updates greatly reduces the number of exploitable entry points available to an attacker.</li><li>Maintain up-to-date antivirus software, and scan all software downloaded from the Internet before executing.</li><li>Restrict users’ abilities (permissions) to install and run unwanted software applications, and apply the principle of “least privilege” to all systems and services. Restricting these privileges may prevent malware from running or limit its capability to spread through the network.</li><li>Avoid enabling macros from email attachments. If a user opens the attachment and enables macros, embedded code will execute the malware on the machine. For enterprises or organizations, it may be best to block email messages with attachments from suspicious sources. For information on safely handling email attachments, see <a href=\"https://www.us-cert.gov/sites/default/files/publications/emailscams_0905.pdf\">Recognizing and Avoiding Email Scams</a>. Follow safe practices when browsing the web. See <a href=\"https://www.us-cert.gov/ncas/tips/ST04-003\">Good Security Habits</a> and <a href=\"https://www.us-cert.gov/ncas/tips/ST06-008\">Safeguarding Your Data</a> for additional details.</li><li>Do not follow unsolicited web links in emails. See <a href=\"https://www.us-cert.gov/ncas/tips/ST04-014\">Avoiding Social Engineering and Phishing Attacks</a> for more information.</li></ul><h4><strong><em>Response to Unauthorized Network Access</em></strong></h4><ul><li><strong>Contact DHS or your local FBI office immediately.</strong> To report an intrusion and request resources for incident response or technical assistance, contact DHS NCCIC (<a href=\"https://www.us-cert.govmailto:NCCICCustomerService@hq.dhs.gov\">NCCICCustomerService@hq.dhs.gov</a> or 888-282-0870), FBI through a local field office, or the FBI’s Cyber Division (<a href=\"https://www.us-cert.govmailto:CyWatch@fbi.gov\">CyWatch@fbi.gov</a> or 855-292-3937).</li></ul>\t\t\n" +
                "\t\t<h3>References</h3>\n" +
                "\t\t<ul>\n" +
                "\t\t\t\t</ul>\n" +
                "\t\t\n" +
                "\t\t<h3>Revision History</h3>\n" +
                "\t\t<ul>\n" +
                "\t\t\t\t\t<li>November 14, 2017: Initial version</li>\n" +
                "\t\t\t\t\t</ul>\n" +
                "\t\t<hr />\n" +
                "\t\t<p>This product is provided subject to this <a href=\"http://www.us-cert.gov/privacy/notification\">Notification</a> and this <a href=\"http://www.us-cert.gov/privacy/\">Privacy &#38; Use</a> policy.</p>\t\t<br />\n" +
                "\t");
        usCertAlert.setPubDate("2017/11/14");


        /* Setting Cisco Alert UP */
        ciscoAlert.setOrigin("Cisco Security Advisory");
        ciscoAlert.setTitle("Cisco Voice Operating System-Based Products Unauthorized Access Vulnerability");
        ciscoAlert.setLink("https://tools.cisco.com/security/center/content/CiscoSecurityAdvisory/cisco-sa-20171115-vos?vs_f=Cisco%20Security%20Advisory&vs_cat=Security%20Intelligence&vs_type=RSS&vs_p=Cisco%20Voice%20Operating%20System-Based%20Products%20Unauthorized%20Access%20Vulnerability&vs_k=1");
        ciscoAlert.setDescription("A vulnerability in the upgrade mechanism of Cisco collaboration products based on the Cisco Voice Operating System software platform could allow an unauthenticated, remote attacker to gain unauthorized, elevated access to an affected device.<br />\n" +
                "<br />\n" +
                "The vulnerability occurs when a refresh upgrade or Prime Collaboration Deployment (PCD) migration is performed on an affected device. When a refresh upgrade or PCD migration is completed successfully, an engineering flag remains enabled and could allow <em>root </em>access to the device with a known password.<br />\n" +
                "<br />\n" +
                "If the vulnerable device is subsequently upgraded using the standard upgrade method to an Engineering Special Release, service update, or a new major release of the affected product, this vulnerability is remediated by that action.<br />\n" +
                "<br />\n" +
                "<strong>Note:</strong>&nbsp;Engineering Special Releases that are installed as COP files, as opposed to the standard upgrade method, do not remediate this vulnerability.<br />\n" +
                "<br />\n" +
                "An attacker who can access an affected device over SFTP while it is in a vulnerable state could gain <em>root </em>access to the device. This access could allow the attacker to compromise the affected system completely.<br />\n" +
                "<br />\n" +
                "Cisco has released software updates that address this vulnerability. There are no workarounds that address this vulnerability.\n" +
                "<br />\n" +
                "<br />\n" +
                "This advisory is available at the following link: <br />\n" +
                "<a href=\"https://tools.cisco.com/security/center/content/CiscoSecurityAdvisory/cisco-sa-20171115-vos\">https://tools.cisco.com/security/center/content/CiscoSecurityAdvisory/cisco-sa-20171115-vos</a>\n" +
                "\t\t\t      \n" +
                "\t\t          <br/>Security Impact Rating:  Critical\n" +
                "\t\t    \n" +
                "\t\t    \n" +
                "\t\t       <br/>CVE: CVE-2017-12337\n" +
                "\t\t    \n" +
                "         ");
        ciscoAlert.setPubDate("2017/11/15");

        /* Setting BugTraq Alert Up */
        bugtraqAlert.setOrigin("Bugtraq");
        bugtraqAlert.setTitle("FreeBSD Security Advisory FreeBSD-SA-17:10.kldstat");
        bugtraqAlert.setLink("http://seclists.org/bugtraq/2017/Nov/48");
        bugtraqAlert.setDescription("<p>Posted by FreeBSD Security Advisories on Nov 16</p>=============================================================================<br>\n" +
                "FreeBSD-SA-17:10.kldstat                                    Security Advisory<br>\n" +
                "                                                          The FreeBSD Project<br>\n" +
                "<br>\n" +
                "Topic:          Information leak in kldstat(2)<br>\n" +
                "<br>\n" +
                "Category:       core<br>\n" +
                "Module:         kernel<br>\n" +
                "Announced:      2017-11-15<br>\n" +
                "Credits:        TJ Corley<br>\n" +
                "Affects:        All supported versions of FreeBSD.<br>\n" +
                "Corrected:...<br>");
        bugtraqAlert.setPubDate("2017/11/17");
    }

    @Test
    public void test1(){
        ArrayList<Email> emails = new ArrayList<Email>();
        emails.add(debianEmail);
        emails.add(vmWareEmail);
        emails.add(fdEmail);
        emails.add(nonValid);
        emails.add(getVmWareEmailDup);

        ArrayList<RSS> rsses = new ArrayList<RSS>();
        rsses.add(usCertAlert);
        rsses.add(ciscoAlert);
        rsses.add(bugtraqAlert);

        ArrayList<HTML> htmls = new ArrayList<HTML>();

        TextMiningMainEntrance textMiningMainEntrance = new TextMiningMainEntrance();
        textMiningMainEntrance.setReceivedEmails(emails);
        textMiningMainEntrance.setReceivedRSS(rsses);
        textMiningMainEntrance.setReceivedHTML(htmls);

        textMiningMainEntrance.performTextMining();

        /*TextMiningMainEntrance textMiningMainEntrance = new TextMiningMainEntrance();
        System.out.println(textMiningMainEntrance.deletePGPSigning(debianEmail.getContent()));
        System.out.println(textMiningMainEntrance.deletePGPSigning(vmWareEmail.getContent()));*/
    }

    }
