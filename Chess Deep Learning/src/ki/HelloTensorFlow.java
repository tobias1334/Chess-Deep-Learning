package ki;

import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

public class HelloTensorFlow {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {
		try (Graph g = new Graph()) {
			final String value = "Version: " + TensorFlow.version();
			try (Tensor t = Tensor.create(value.getBytes("UTF-8"))) {
				g.opBuilder("Const", "const1").setAttr("dtype", t.dataType()).setAttr("value", t).build();
			}
			
			try (Session s = new Session(g);
					Tensor output = s.runner().fetch("const1").run().get(0)) {
				System.out.println(new String(output.bytesValue(), "UTF-8"));
			}

		}
		
		System.out.println("Finished!");
	}
}
