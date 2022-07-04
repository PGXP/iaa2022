/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.feiraml;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import org.tensorflow.EagerSession;
import org.tensorflow.Graph;
import org.tensorflow.Operand;
import org.tensorflow.Session;
import org.tensorflow.Session.Runner;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.op.Op;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.core.Variable;
import org.tensorflow.op.math.Add;
import org.tensorflow.op.math.Mean;
import org.tensorflow.op.random.RandomStandardNormal;
import org.tensorflow.types.TFloat64;
import org.tensorflow.types.family.TType;

/**
 *
 * @author gladson
 */
public final class Test {

    private static final int TRAINING_ITERATIONS = 10;
    private static final float LEARNING_RATE = 0.5f;

    public static void main(String[] args) {
        Instant startTime = Instant.now();
        System.out.println("TensorFlow " + TensorFlow.version());

        TFloat64 xEvaluated = TFloat64.tensorOf(Shape.of(2000, 3));
        TFloat64 yEvaluated = TFloat64.tensorOf(Shape.of(2000));
        try ( EagerSession es = EagerSession.create()) {
            Ops ops = Ops.create(es);
            RandomStandardNormal<TFloat64> xData
                    = ops.withName("xData").random
                            .randomStandardNormal(ops.constant(new int[]{2000, 3}), TFloat64.class);
            Operand<TFloat64> wReal = ops.withName("wReal").constant(new double[]{0.3, 0.5, 0.1});
            Operand<TFloat64> bReal = ops.withName("bReal").constant(-0.2);
            Operand<TFloat64> noise
                    = ops.withName("noise").math
                            .add(
                                    ops.random.randomStandardNormal(
                                            ops.constant(new int[]{2000}), TFloat64.class),
                                    ops.constant(0.1));
            Operand<TFloat64> yProj = ops.squeeze(ops.linalg.matMul(xData.asOutput(), ops.expandDims(wReal, ops.constant(-1))));
            Operand<TFloat64> yData = ops.withName("yData").math.add(ops.math.add(yProj, bReal), noise);
            System.out.println("x shape " + xData.shape().toString());
            System.out.println("yproj shape " + yProj.shape().toString());
            System.out.println("y shape " + yData.shape().toString());
            xData.asTensor().copyTo(xEvaluated);
            yData.asTensor().copyTo(yEvaluated);
        }

        try ( Graph graph = new Graph();  Session session = new Session(graph)) {
            Ops ops = Ops.create(graph);
            Placeholder<TFloat64> x
                    = ops.withName("x")
                            .placeholder(TFloat64.class, Placeholder.shape(Shape.of(Shape.UNKNOWN_SIZE, 3)));
            Placeholder<TFloat64> y
                    = ops.withName("y")
                            .placeholder(TFloat64.class, Placeholder.shape(Shape.of(Shape.UNKNOWN_SIZE)));

            Ops inferenceOps = ops.withSubScope("inference");
            Variable<TFloat64> weights
                    = inferenceOps.variable(
                            inferenceOps.constant(new double[]{0, 0, 0}), Variable.sharedName("weights"));
            Variable<TFloat64> bias
                    = inferenceOps.variable(inferenceOps.constant(0.0), Variable.sharedName("bias"));
            Add<TFloat64> yPredicted
                    = inferenceOps
                            .withName("yPredicted").math
                            .add(ops.squeeze(ops.linalg.matMul(x.asOutput(), ops.expandDims(weights, ops.constant(-1)))), bias);

            Ops lossOps = ops.withSubScope("loss");
            Mean<TFloat64> loss
                    = lossOps.math.mean(
                            lossOps.math.square(lossOps.math.sub(y, yPredicted)), lossOps.constant(0));

//            Optimizer optimizer = new GradientDescent(graph, "GradientDescent", LEARNING_RATE);
//            Op train = optimizer.minimize(loss);
//            session.run(ops.init());
            for (int i = 0; i < TRAINING_ITERATIONS; ++i) {
                Runner runner = session.runner();
                System.out.println("x:\n" + x.shape());
                System.out.println("y:\n" + y.shape());
                List<Tensor> evaluated
                        = runner
                                //                                .addTarget(train)
                                .feed(x, xEvaluated)
                                .feed(y, yEvaluated)
                                .fetch(loss)
                                .fetch(weights)
                                .fetch(bias)
                                .run();
                TType lossEvaluated = (TType) evaluated.get(0);
                TFloat64 weightsEvaluated = (TFloat64) evaluated.get(1);
                TFloat64 biasEvaluated = (TFloat64) evaluated.get(2);
                System.out.print("w = ");
                weightsEvaluated.scalars().forEach((scalar) -> {
                    System.out.print(scalar.getDouble() + ", ");
                }
                );
                System.out.println("");
                System.out.println("b = " + biasEvaluated.getDouble());
                lossEvaluated.close();
                weightsEvaluated.close();
                biasEvaluated.close();
            }
        }
        Instant endTime = Instant.now();
        System.out.println("Time elapsed: " + Duration.between(startTime, endTime));
    }
}
