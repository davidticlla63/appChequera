package bo.com.erp360.util;

public class StreamUtil
{
  public static final String PREFIX = "stream2file";
  public static final String SUFFIX = ".tmp";
  
  /* Error */
  public static java.io.File stream2file(java.io.InputStream in)
    throws java.io.IOException
  {
    // Byte code:
    //   0: ldc 8
    //   2: ldc 11
    //   4: invokestatic 26	java/io/File:createTempFile	(Ljava/lang/String;Ljava/lang/String;)Ljava/io/File;
    //   7: astore_1
    //   8: aload_1
    //   9: invokevirtual 32	java/io/File:deleteOnExit	()V
    //   12: aconst_null
    //   13: astore_2
    //   14: aconst_null
    //   15: astore_3
    //   16: new 35	java/io/FileOutputStream
    //   19: dup
    //   20: aload_1
    //   21: invokespecial 37	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   24: astore 4
    //   26: aload_0
    //   27: aload 4
    //   29: invokestatic 40	org/apache/commons/io/IOUtils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)I
    //   32: pop
    //   33: aload 4
    //   35: ifnull +46 -> 81
    //   38: aload 4
    //   40: invokevirtual 46	java/io/FileOutputStream:close	()V
    //   43: goto +38 -> 81
    //   46: astore_2
    //   47: aload 4
    //   49: ifnull +8 -> 57
    //   52: aload 4
    //   54: invokevirtual 46	java/io/FileOutputStream:close	()V
    //   57: aload_2
    //   58: athrow
    //   59: astore_3
    //   60: aload_2
    //   61: ifnonnull +8 -> 69
    //   64: aload_3
    //   65: astore_2
    //   66: goto +13 -> 79
    //   69: aload_2
    //   70: aload_3
    //   71: if_acmpeq +8 -> 79
    //   74: aload_2
    //   75: aload_3
    //   76: invokevirtual 49	java/lang/Throwable:addSuppressed	(Ljava/lang/Throwable;)V
    //   79: aload_2
    //   80: athrow
    //   81: aload_1
    //   82: areturn
    // Line number table:
    //   Java source line #21	-> byte code offset #0
    //   Java source line #22	-> byte code offset #8
    //   Java source line #23	-> byte code offset #12
    //   Java source line #23	-> byte code offset #16
    //   Java source line #24	-> byte code offset #26
    //   Java source line #25	-> byte code offset #33
    //   Java source line #26	-> byte code offset #81
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	83	0	in	java.io.InputStream
    //   7	75	1	tempFile	java.io.File
    //   13	1	2	localObject1	Object
    //   46	15	2	localObject2	Object
    //   65	15	2	localObject3	Object
    //   15	1	3	localObject4	Object
    //   59	17	3	localThrowable	Throwable
    //   24	29	4	out	java.io.FileOutputStream
    // Exception table:
    //   from	to	target	type
    //   26	33	46	finally
    //   16	59	59	finally
  }
}
