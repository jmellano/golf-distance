package com.bettergolf.domain;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CalibrationTest {
    @Test
    public void updateCalibrationPlayerClub() throws Exception {

        Calibration calibration = new Calibration();
        List<Shot> shots = new ArrayList<>();

        for(int i = 10; i<=100; i=i+10) {
            Shot shotToAdd = new Shot();
            shotToAdd.setDistance(new BigDecimal(i));
            shots.add(shotToAdd);
        }

        calibration.updateCalibrationPlayerClub(shots);


        Assert.assertTrue(calibration.getAverage().equals(new BigDecimal(55)));
        Assert.assertEquals(new BigDecimal(30.28).setScale(2, RoundingMode.HALF_UP), calibration.getStandardDeviation());
    }

}
