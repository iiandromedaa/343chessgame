package cids.grouptwo.gdx;

import com.crashinvaders.vfx.VfxRenderContext;
import com.crashinvaders.vfx.effects.GaussianBlurEffect;
import com.crashinvaders.vfx.framebuffer.VfxPingPongWrapper;

/**
 * vfx library i grabbed doesn't even let you set custom blur radii, which is BS
 * so im doing it myself <br> might scrap this, but it feels wrong using 5 passes of blur
 * because the orignal class couldnt just have a user-defined radius
 */
public class CustomGaussianBlur extends GaussianBlurEffect {

    private int radius;
    private float invWidth, invHeight;
    private Convolve2DEffect convolve;
    
    public CustomGaussianBlur(int radius) {
        this.radius = radius;
        convolve = new Convolve2DEffect(radius);
        computeBlurWeightings();
    }

    private void computeBlurWeightings() {
        boolean hasData = true;

        float[] outWeights = convolve.getWeights();
        float[] outOffsetsH = convolve.getOffsetsHor();
        float[] outOffsetsV = convolve.getOffsetsVert();

        computeKernel(radius, this.getAmount(), outWeights);
        computeOffsets(radius, this.invWidth, this.invHeight, outOffsetsH, outOffsetsV);

        if (hasData) {
            convolve.rebind();
        }
    }

    private void computeKernel(int blurRadius, float blurAmount, float[] outKernel) {
        int radius = blurRadius;

        // float sigma = (float)radius / amount;
        float sigma = blurAmount;

        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        float distance = 0.0f;
        int index = 0;

        for (int i = -radius; i <= radius; ++i) {
            distance = i * i;
            index = i + radius;
            outKernel[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += outKernel[index];
        }

        int size = (radius * 2) + 1;
        for (int i = 0; i < size; ++i) {
            outKernel[i] /= total;
        }
    }

    private void computeOffsets(int blurRadius, float dx, float dy, float[] outOffsetH, float[] outOffsetV) {
        int radius = blurRadius;

        final int X = 0, Y = 1;
        for (int i = -radius, j = 0; i <= radius; ++i, j += 2) {
            outOffsetH[j + X] = i * dx;
            outOffsetH[j + Y] = 0;

            outOffsetV[j + X] = 0;
            outOffsetV[j + Y] = i * dy;
        }
    }

    @Override
    public void resize(int width, int height) {
        this.invWidth = 1f / (float) width;
        this.invHeight = 1f / (float) height;

        convolve.resize(width, height);
        computeBlurWeightings();
    }

    @Override
    public void rebind() {
        convolve.rebind();
        computeBlurWeightings();
    }

    @Override
    public void render(VfxRenderContext context, VfxPingPongWrapper buffers) {
        for (int i = 0; i < this.getPasses(); i++) {
            convolve.render(context, buffers);

            if (i < this.getPasses() - 1) {
                buffers.swap();
            }
        }
    }

    @Override
    public void setAmount(float amount) {
        super.setAmount(amount);
    }

}
