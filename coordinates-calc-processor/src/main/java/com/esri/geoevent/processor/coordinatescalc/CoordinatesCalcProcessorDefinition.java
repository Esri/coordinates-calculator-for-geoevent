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
    return "10.3.0";
  }
	
	@Override
  public String getLabel()
  {
    return "${com.esri.geoevent.processor.coordinates-calc-processor.PROCESSOR_LABEL}";
  }
	
	@Override
  public String getDescription()
  {
    return "${com.esri.geoevent.processor.coordinates-calc-processor.PROCESSOR_DESC}";
  }

  @Override
  public String getContactInfo()
  {
    return "geoeventprocessor@esri.com";
  }
}