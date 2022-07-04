/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.feiraml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.types.TFloat32;

/**
 *
 * @author gladson
 */
public class Example {

    public static void main(String[] args) {

        int ITERATION = 1000;
        String dir = "model_path";

        SavedModelBundle.Loader loader
                = SavedModelBundle.loader(dir).withTags("serve");

        SavedModelBundle bundle = loader.load();
        Session session = bundle.session();
        List<Long> timeCollector = new ArrayList<>();
        for (int i = 0; i < ITERATION; i++) {
            long start = System.nanoTime();
            forward(session);
            timeCollector.add(System.nanoTime() - start);
        }
        Collections.sort(timeCollector);
        System.out.println("P50: " + percentile(timeCollector, 50) + "ms");
        System.out.println("P90: " + percentile(timeCollector, 90) + "ms");
        System.out.println("P99: " + percentile(timeCollector, 99) + "ms");
    }

    public static double percentile(List<Long> times, int percentile) {
        int index = times.size() * percentile / 100;
        return times.get(index) / 1_000_000f;
    }

    public static void forward(Session session) {
        Session.Runner runner = session.runner();
//        try ( Tensor<?> tensor = Tensor.of(TFloat32.DTYPE, Shape.of(1, 224, 224, 3))) {
//            runner.feed("serving_default_input_1:0", tensor);
//            runner.fetch("StatefulPartitionedCall:0");
//            List<Tensor<?>> result = runner.run();
//        }
    }
}
