package com.esri.geoevent.processor.coordinatescalc;

import com.esri.ges.processor.GeoEventProcessorDefinitionBase;

public class CoordinatesCalcProcessorDefinition extends GeoEventProcessorDefinitionBase
{
	public CoordinatesCalcProcessorDefinition()
	{
		;
	}
	
	@Override
	public String getName()
	{
		return "CoordinatesCalculationProcessor";
	}
	
	@Override
  public String getDomain()
  {
    return "com.esri.geoevent.processor";
  }
	
	@Override
  public String getVersion()
  {
    return "10.2.0";
  }
	
	@Override
  public String getLabel()
  {
    return "Coordinates Calculation Processor";
  }
	
	@Override
  public String getDescription()
  {
    return "This is a Coordinates Calculation Processorr.";
  }

  @Override
  public String getContactInfo()
  {
    return "geoeventprocessor@esri.com";
  }
}