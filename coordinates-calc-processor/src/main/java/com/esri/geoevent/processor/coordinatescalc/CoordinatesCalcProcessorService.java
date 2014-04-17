package com.esri.geoevent.processor.coordinatescalc;

import com.esri.ges.core.component.ComponentException;
import com.esri.ges.processor.GeoEventProcessor;
import com.esri.ges.processor.GeoEventProcessorServiceBase;

public class CoordinatesCalcProcessorService extends GeoEventProcessorServiceBase
{
  public CoordinatesCalcProcessorService()
  {
    definition = new CoordinatesCalcProcessorDefinition();
  }

  @Override
  public GeoEventProcessor create() throws ComponentException
  {
    return new CoordinatesCalcProcessor(definition);
  }
}