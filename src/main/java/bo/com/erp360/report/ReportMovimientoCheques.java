package bo.com.erp360.report;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet({"/ReportMovimientoCheques"})
public class ReportMovimientoCheques
  extends HttpServlet
{
  private static final long serialVersionUID = -4777861118160332227L;
  @Inject
  private EntityManager em;
  
  /* Error */
  protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
    throws javax.servlet.ServletException, java.io.IOException
  {
    // Byte code:
    //   0: aload_2
    //   1: invokeinterface 30 1 0
    //   6: astore_3
    //   7: aconst_null
    //   8: astore 4
    //   10: aconst_null
    //   11: astore 7
    //   13: aconst_null
    //   14: astore 8
    //   16: new 36	javax/naming/InitialContext
    //   19: dup
    //   20: invokespecial 38	javax/naming/InitialContext:<init>	()V
    //   23: astore 9
    //   25: aload 9
    //   27: getstatic 39	bo/com/erp360/util/Conexion:datasourse	Ljava/lang/String;
    //   30: invokeinterface 45 2 0
    //   35: checkcast 51	javax/sql/DataSource
    //   38: astore 10
    //   40: aload 10
    //   42: invokeinterface 53 1 0
    //   47: astore 4
    //   49: aload 4
    //   51: ifnull +14 -> 65
    //   54: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   57: ldc 63
    //   59: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   62: goto +42 -> 104
    //   65: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   68: ldc 71
    //   70: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   73: goto +31 -> 104
    //   76: astore 9
    //   78: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   81: new 73	java/lang/StringBuilder
    //   84: dup
    //   85: ldc 75
    //   87: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   90: aload 9
    //   92: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   95: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   104: aload_1
    //   105: ldc 92
    //   107: invokeinterface 94 2 0
    //   112: astore 9
    //   114: aload_1
    //   115: ldc 100
    //   117: invokeinterface 94 2 0
    //   122: invokestatic 102	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   125: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   128: astore 10
    //   130: aload_1
    //   131: ldc 112
    //   133: invokeinterface 94 2 0
    //   138: astore 11
    //   140: aload_1
    //   141: ldc 114
    //   143: invokeinterface 94 2 0
    //   148: invokestatic 102	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   151: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   154: astore 12
    //   156: aload_1
    //   157: ldc 116
    //   159: invokeinterface 94 2 0
    //   164: invokestatic 102	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   167: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   170: astore 13
    //   172: aload_1
    //   173: ldc 118
    //   175: invokeinterface 94 2 0
    //   180: astore 14
    //   182: aload_1
    //   183: ldc 120
    //   185: invokeinterface 94 2 0
    //   190: astore 15
    //   192: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   195: new 73	java/lang/StringBuilder
    //   198: dup
    //   199: ldc 122
    //   201: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   204: aload_0
    //   205: getfield 124	bo/com/erp360/report/ReportMovimientoCheques:em	Ljavax/persistence/EntityManager;
    //   208: invokeinterface 126 1 0
    //   213: invokevirtual 132	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   216: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   219: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   222: aload_1
    //   223: ldc -121
    //   225: invokeinterface 137 2 0
    //   230: astore 16
    //   232: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   235: new 73	java/lang/StringBuilder
    //   238: dup
    //   239: ldc -116
    //   241: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   244: aload 16
    //   246: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   249: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   252: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   255: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   258: new 73	java/lang/StringBuilder
    //   261: dup
    //   262: ldc -114
    //   264: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   267: aload_1
    //   268: invokeinterface 144 1 0
    //   273: invokeinterface 148 1 0
    //   278: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   287: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   290: new 73	java/lang/StringBuilder
    //   293: dup
    //   294: ldc -103
    //   296: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   299: aload_1
    //   300: invokeinterface 155 1 0
    //   305: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   308: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   311: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   314: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   317: new 73	java/lang/StringBuilder
    //   320: dup
    //   321: ldc -98
    //   323: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   326: aload_1
    //   327: invokeinterface 160 1 0
    //   332: invokeinterface 164 1 0
    //   337: ldc -121
    //   339: invokeinterface 167 2 0
    //   344: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   347: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   350: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   353: aload_1
    //   354: invokeinterface 168 1 0
    //   359: invokevirtual 172	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   362: astore 17
    //   364: new 73	java/lang/StringBuilder
    //   367: dup
    //   368: aload 17
    //   370: iconst_0
    //   371: aload 17
    //   373: invokevirtual 175	java/lang/String:length	()I
    //   376: aload_1
    //   377: invokeinterface 181 1 0
    //   382: invokevirtual 175	java/lang/String:length	()I
    //   385: isub
    //   386: invokevirtual 184	java/lang/String:substring	(II)Ljava/lang/String;
    //   389: invokestatic 188	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   392: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   395: aload_1
    //   396: invokeinterface 191 1 0
    //   401: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   404: ldc -121
    //   406: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   412: astore 17
    //   414: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   417: new 73	java/lang/StringBuilder
    //   420: dup
    //   421: ldc -64
    //   423: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   426: aload 17
    //   428: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   431: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   434: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   437: new 73	java/lang/StringBuilder
    //   440: dup
    //   441: aload 17
    //   443: invokestatic 188	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   446: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   449: ldc -62
    //   451: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   454: iconst_1
    //   455: invokevirtual 196	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   458: ldc -57
    //   460: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   463: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   466: astore 18
    //   468: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   471: new 73	java/lang/StringBuilder
    //   474: dup
    //   475: ldc -55
    //   477: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   480: aload 18
    //   482: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   485: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   488: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   491: new 203	java/util/HashMap
    //   494: dup
    //   495: invokespecial 205	java/util/HashMap:<init>	()V
    //   498: astore 19
    //   500: aload 19
    //   502: ldc -50
    //   504: aload 10
    //   506: invokeinterface 208 3 0
    //   511: pop
    //   512: aload 19
    //   514: ldc -42
    //   516: aload 11
    //   518: invokeinterface 208 3 0
    //   523: pop
    //   524: aload 19
    //   526: ldc -40
    //   528: aload 13
    //   530: invokeinterface 208 3 0
    //   535: pop
    //   536: aload 19
    //   538: ldc -38
    //   540: aload 15
    //   542: invokeinterface 208 3 0
    //   547: pop
    //   548: aload 19
    //   550: ldc -36
    //   552: aload 12
    //   554: invokeinterface 208 3 0
    //   559: pop
    //   560: aload 19
    //   562: ldc -34
    //   564: aload 14
    //   566: invokeinterface 208 3 0
    //   571: pop
    //   572: new 73	java/lang/StringBuilder
    //   575: dup
    //   576: aload 17
    //   578: invokestatic 188	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   581: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   584: ldc -32
    //   586: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   589: aload 9
    //   591: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: ldc -30
    //   596: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   599: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   602: astore 20
    //   604: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   607: new 73	java/lang/StringBuilder
    //   610: dup
    //   611: ldc -28
    //   613: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   616: aload 19
    //   618: invokevirtual 230	java/lang/Object:toString	()Ljava/lang/String;
    //   621: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   624: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   627: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   630: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   633: new 73	java/lang/StringBuilder
    //   636: dup
    //   637: ldc -23
    //   639: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   642: aload 20
    //   644: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   647: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   650: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   653: new 235	java/net/URL
    //   656: dup
    //   657: aload 20
    //   659: invokespecial 237	java/net/URL:<init>	(Ljava/lang/String;)V
    //   662: invokestatic 238	net/sf/jasperreports/engine/util/JRLoader:loadObject	(Ljava/net/URL;)Ljava/lang/Object;
    //   665: checkcast 244	net/sf/jasperreports/engine/JasperReport
    //   668: astore 5
    //   670: aload 5
    //   672: aload 19
    //   674: aload 4
    //   676: invokestatic 246	net/sf/jasperreports/engine/JasperFillManager:fillReport	(Lnet/sf/jasperreports/engine/JasperReport;Ljava/util/Map;Ljava/sql/Connection;)Lnet/sf/jasperreports/engine/JasperPrint;
    //   679: astore 6
    //   681: aload_2
    //   682: ldc -4
    //   684: invokeinterface 254 2 0
    //   689: aload 6
    //   691: aload_3
    //   692: invokestatic 257	net/sf/jasperreports/engine/JasperExportManager:exportReportToPdfStream	(Lnet/sf/jasperreports/engine/JasperPrint;Ljava/io/OutputStream;)V
    //   695: aload_3
    //   696: invokevirtual 263	javax/servlet/ServletOutputStream:flush	()V
    //   699: aload_3
    //   700: invokevirtual 268	javax/servlet/ServletOutputStream:close	()V
    //   703: goto +197 -> 900
    //   706: astore 9
    //   708: aload 9
    //   710: invokevirtual 271	java/lang/Exception:printStackTrace	()V
    //   713: getstatic 57	java/lang/System:out	Ljava/io/PrintStream;
    //   716: new 73	java/lang/StringBuilder
    //   719: dup
    //   720: ldc_w 274
    //   723: invokespecial 77	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   726: aload 9
    //   728: invokevirtual 79	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   731: invokevirtual 85	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   734: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   737: invokevirtual 65	java/io/PrintStream:println	(Ljava/lang/String;)V
    //   740: new 276	java/io/StringWriter
    //   743: dup
    //   744: invokespecial 278	java/io/StringWriter:<init>	()V
    //   747: astore 10
    //   749: new 279	java/io/PrintWriter
    //   752: dup
    //   753: aload 10
    //   755: invokespecial 281	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   758: astore 11
    //   760: aload 9
    //   762: aload 11
    //   764: invokevirtual 284	java/lang/Exception:printStackTrace	(Ljava/io/PrintWriter;)V
    //   767: aload_2
    //   768: ldc_w 287
    //   771: invokeinterface 254 2 0
    //   776: aload_2
    //   777: invokeinterface 30 1 0
    //   782: aload 10
    //   784: invokevirtual 289	java/io/StringWriter:toString	()Ljava/lang/String;
    //   787: invokevirtual 290	javax/servlet/ServletOutputStream:print	(Ljava/lang/String;)V
    //   790: goto +110 -> 900
    //   793: astore 9
    //   795: aload 9
    //   797: invokevirtual 271	java/lang/Exception:printStackTrace	()V
    //   800: aload 8
    //   802: ifnull +10 -> 812
    //   805: aload 8
    //   807: invokeinterface 293 1 0
    //   812: aload 7
    //   814: ifnull +10 -> 824
    //   817: aload 7
    //   819: invokeinterface 296 1 0
    //   824: aload 4
    //   826: ifnull +120 -> 946
    //   829: aload 4
    //   831: invokeinterface 299 1 0
    //   836: goto +110 -> 946
    //   839: astore 22
    //   841: aload 22
    //   843: invokevirtual 302	java/sql/SQLException:printStackTrace	()V
    //   846: goto +100 -> 946
    //   849: astore 21
    //   851: aload 8
    //   853: ifnull +10 -> 863
    //   856: aload 8
    //   858: invokeinterface 293 1 0
    //   863: aload 7
    //   865: ifnull +10 -> 875
    //   868: aload 7
    //   870: invokeinterface 296 1 0
    //   875: aload 4
    //   877: ifnull +20 -> 897
    //   880: aload 4
    //   882: invokeinterface 299 1 0
    //   887: goto +10 -> 897
    //   890: astore 22
    //   892: aload 22
    //   894: invokevirtual 302	java/sql/SQLException:printStackTrace	()V
    //   897: aload 21
    //   899: athrow
    //   900: aload 8
    //   902: ifnull +10 -> 912
    //   905: aload 8
    //   907: invokeinterface 293 1 0
    //   912: aload 7
    //   914: ifnull +10 -> 924
    //   917: aload 7
    //   919: invokeinterface 296 1 0
    //   924: aload 4
    //   926: ifnull +20 -> 946
    //   929: aload 4
    //   931: invokeinterface 299 1 0
    //   936: goto +10 -> 946
    //   939: astore 22
    //   941: aload 22
    //   943: invokevirtual 302	java/sql/SQLException:printStackTrace	()V
    //   946: return
    // Line number table:
    //   Java source line #44	-> byte code offset #0
    //   Java source line #45	-> byte code offset #7
    //   Java source line #49	-> byte code offset #10
    //   Java source line #50	-> byte code offset #13
    //   Java source line #53	-> byte code offset #16
    //   Java source line #54	-> byte code offset #25
    //   Java source line #55	-> byte code offset #27
    //   Java source line #54	-> byte code offset #35
    //   Java source line #56	-> byte code offset #40
    //   Java source line #58	-> byte code offset #49
    //   Java source line #59	-> byte code offset #54
    //   Java source line #60	-> byte code offset #62
    //   Java source line #61	-> byte code offset #65
    //   Java source line #64	-> byte code offset #73
    //   Java source line #65	-> byte code offset #78
    //   Java source line #73	-> byte code offset #104
    //   Java source line #74	-> byte code offset #114
    //   Java source line #75	-> byte code offset #130
    //   Java source line #76	-> byte code offset #140
    //   Java source line #77	-> byte code offset #156
    //   Java source line #78	-> byte code offset #172
    //   Java source line #79	-> byte code offset #182
    //   Java source line #81	-> byte code offset #192
    //   Java source line #83	-> byte code offset #222
    //   Java source line #84	-> byte code offset #232
    //   Java source line #88	-> byte code offset #255
    //   Java source line #89	-> byte code offset #267
    //   Java source line #88	-> byte code offset #284
    //   Java source line #90	-> byte code offset #287
    //   Java source line #91	-> byte code offset #299
    //   Java source line #90	-> byte code offset #311
    //   Java source line #92	-> byte code offset #314
    //   Java source line #93	-> byte code offset #317
    //   Java source line #94	-> byte code offset #337
    //   Java source line #93	-> byte code offset #347
    //   Java source line #96	-> byte code offset #353
    //   Java source line #97	-> byte code offset #364
    //   Java source line #98	-> byte code offset #414
    //   Java source line #100	-> byte code offset #437
    //   Java source line #102	-> byte code offset #468
    //   Java source line #104	-> byte code offset #491
    //   Java source line #107	-> byte code offset #500
    //   Java source line #109	-> byte code offset #512
    //   Java source line #110	-> byte code offset #524
    //   Java source line #111	-> byte code offset #536
    //   Java source line #112	-> byte code offset #548
    //   Java source line #113	-> byte code offset #560
    //   Java source line #115	-> byte code offset #572
    //   Java source line #117	-> byte code offset #604
    //   Java source line #118	-> byte code offset #630
    //   Java source line #122	-> byte code offset #653
    //   Java source line #123	-> byte code offset #657
    //   Java source line #122	-> byte code offset #659
    //   Java source line #126	-> byte code offset #670
    //   Java source line #127	-> byte code offset #672
    //   Java source line #126	-> byte code offset #676
    //   Java source line #129	-> byte code offset #681
    //   Java source line #130	-> byte code offset #689
    //   Java source line #131	-> byte code offset #691
    //   Java source line #130	-> byte code offset #692
    //   Java source line #133	-> byte code offset #695
    //   Java source line #134	-> byte code offset #699
    //   Java source line #136	-> byte code offset #703
    //   Java source line #138	-> byte code offset #708
    //   Java source line #139	-> byte code offset #713
    //   Java source line #140	-> byte code offset #726
    //   Java source line #139	-> byte code offset #737
    //   Java source line #141	-> byte code offset #740
    //   Java source line #142	-> byte code offset #749
    //   Java source line #143	-> byte code offset #760
    //   Java source line #144	-> byte code offset #767
    //   Java source line #145	-> byte code offset #776
    //   Java source line #149	-> byte code offset #790
    //   Java source line #150	-> byte code offset #795
    //   Java source line #154	-> byte code offset #800
    //   Java source line #155	-> byte code offset #805
    //   Java source line #156	-> byte code offset #812
    //   Java source line #157	-> byte code offset #817
    //   Java source line #158	-> byte code offset #824
    //   Java source line #159	-> byte code offset #829
    //   Java source line #160	-> byte code offset #836
    //   Java source line #161	-> byte code offset #841
    //   Java source line #152	-> byte code offset #849
    //   Java source line #154	-> byte code offset #851
    //   Java source line #155	-> byte code offset #856
    //   Java source line #156	-> byte code offset #863
    //   Java source line #157	-> byte code offset #868
    //   Java source line #158	-> byte code offset #875
    //   Java source line #159	-> byte code offset #880
    //   Java source line #160	-> byte code offset #887
    //   Java source line #161	-> byte code offset #892
    //   Java source line #163	-> byte code offset #897
    //   Java source line #154	-> byte code offset #900
    //   Java source line #155	-> byte code offset #905
    //   Java source line #156	-> byte code offset #912
    //   Java source line #157	-> byte code offset #917
    //   Java source line #158	-> byte code offset #924
    //   Java source line #159	-> byte code offset #929
    //   Java source line #160	-> byte code offset #936
    //   Java source line #161	-> byte code offset #941
    //   Java source line #165	-> byte code offset #946
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	947	0	this	ReportMovimientoCheques
    //   0	947	1	request	javax.servlet.http.HttpServletRequest
    //   0	947	2	response	javax.servlet.http.HttpServletResponse
    //   6	694	3	servletOutputStream	javax.servlet.ServletOutputStream
    //   8	922	4	conn	java.sql.Connection
    //   668	3	5	jasperReport	net.sf.jasperreports.engine.JasperReport
    //   679	11	6	jasperPrint	net.sf.jasperreports.engine.JasperPrint
    //   11	907	7	stmt	java.sql.Statement
    //   14	892	8	rs	java.sql.ResultSet
    //   23	3	9	ctx	javax.naming.Context
    //   76	15	9	e	Exception
    //   112	478	9	pTipoReporte	String
    //   706	55	9	e	Exception
    //   793	3	9	e	Exception
    //   38	3	10	ds	javax.sql.DataSource
    //   128	377	10	pIdEmpresa	Integer
    //   747	36	10	stringWriter	java.io.StringWriter
    //   138	379	11	pNombreEmpresa	String
    //   758	5	11	printWriter	java.io.PrintWriter
    //   154	399	12	pFechaInicio	Integer
    //   170	359	13	pFechaFin	Integer
    //   180	385	14	pFechaInicioTxt	String
    //   190	351	15	pFechaFinTxt	String
    //   230	15	16	realPath	String
    //   362	215	17	urlPath	String
    //   466	15	18	URL_SERVLET_LOGO	String
    //   498	175	19	parameters	java.util.Map
    //   602	56	20	rutaReporte	String
    //   849	49	21	localObject	Object
    //   839	3	22	e	java.sql.SQLException
    //   890	3	22	e	java.sql.SQLException
    //   939	3	22	e	java.sql.SQLException
    // Exception table:
    //   from	to	target	type
    //   16	73	76	java/lang/Exception
    //   104	703	706	java/lang/Exception
    //   104	790	793	java/lang/Exception
    //   800	836	839	java/sql/SQLException
    //   104	800	849	finally
    //   851	887	890	java/sql/SQLException
    //   900	936	939	java/sql/SQLException
  }
}
