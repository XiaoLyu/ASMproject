package ASMproject.project;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
public class CollectMetrics {

    public static void main(String args[]) throws Exception {

        System.out.println(String.join(",", Arrays.asList(new String[] { "methodName", "linesOfCode"})));
//		for (int i=0;i< args.length;i++) {
//			String arg = args[i];
        String arg = "resource/aalto-xml/classes/com/fasterxml/aalto/async/AsyncByteScanner.class";
        System.out.printf("Calculating metrics for class file %s\n", arg);
        FileInputStream is = new FileInputStream(arg);

        ClassReader reader = new ClassReader(is);

        ClassNode classNode = new ClassNode();
        reader.accept(classNode, 0);

        System.out.printf("Calculating metrics for each method");

        for (MethodNode method : (List<MethodNode>) classNode.methods) {
            String metrics = collectMetrics(classNode, method);
            System.out.println(metrics);
        }

//		}

    }

    private static String collectMetrics(ClassNode classNode, MethodNode method) {

        // collect class names and methods' names
        String methodName = String.format("%s.%s", classNode.name, method.name);

        // collect arguments
        Arguments arguments = new Arguments(null);
        method.accept(arguments);
        int numOfArguments = arguments.getArgumentNum();

        // collect variable declarations and variable references
        Variable vd = new Variable(null);
        method.accept(vd);
        int variableDelarationNum = vd.getVariableDeclarationNum();
        int variableReferenceNum = vd.getVariableReferenceNum();

        // collect Halstead length
        Halstead halstead = new Halstead(null);
        method.accept(halstead);
        long halsteadLength = halstead.info.HalsteadLength();

        // collect Halstead vocabulary
        long halsteadVocabulary = halstead.info.HalsteadVocabulary();

        // collect Halstead volume
        double halsteadVolume = halstead.info.HalsteadVolume();

        // collect Halstead difficulty
        double halsteadDifficulty = halstead.info.HalsteadDifficulty();

        // collect Halstead effort
        double halsteadEffort = halstead.info.HalsteadEffort();

        // collect Halstead bugs
        double halsteadBugs = halstead.info.HalsteadBugs();

        // collect number of casting
        Casting casting = new Casting(null);
        method.accept(casting);
        int castingNum = casting.getCastingNum();

        // collect number of operators
        long numOfOperators = halstead.info.operatorsNum();

        // collect number of operands
        long numOfOperands = halstead.info.operandsNum();

        // collect unique number of operators
        long uniOperators = halstead.info.uniqueOperatorsNum();

        // collect unique number of operands
        long uniOperands = halstead.info.uniqueOperandsNum();

        // class references
        ClassReferences cl = new ClassReferences(null);
        method.accept(cl);
        String classReferenceNames = cl.getClassReferencesNames();

        // collect exception information
        // exception referenced
        ExceptionReference exceptionInfo = new ExceptionReference(null);
        method.accept(exceptionInfo);
        //      List<String> exinfo = exceptionInfo.getExceptionsNameList();
        String exName = exceptionInfo.getExceptionsName();

        // exception thrown
        ExceptionThrown ex = new ExceptionThrown(null);
        method.accept(ex);
//        List<String> exList = ex.getNames();
        String exThrown = ex.getExceptionThrown();

        // collect modifiers
        Modifiers modifiers = new Modifiers(null);
        method.accept(modifiers);
        String modi = modifiers.getModifier();

        // collect lines of code
        LinesOfCode lineCount = new LinesOfCode(null);
        method.accept(lineCount);
        int lines = lineCount.getLines();

        String result = methodName + "," + numOfArguments + "," + variableDelarationNum +
                "," + variableReferenceNum + ","+
                halsteadLength + "," + halsteadVocabulary + "," + halsteadVolume + ","
                + halsteadDifficulty + "," + halsteadEffort + "," +
                halsteadBugs + "," + castingNum + "," + numOfOperators + "," + numOfOperands + ","
                + uniOperators + "," + uniOperands + "," + classReferenceNames +  "," + exName + ","
                + exThrown + "," + modi + "," + lines;

        return result;
    }

}