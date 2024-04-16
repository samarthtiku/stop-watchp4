package edu.luc.etl.cs313.android.simplecountdown.model;

import edu.luc.etl.cs313.android.simplecountdown.common.Startable;
import edu.luc.etl.cs313.android.simplecountdown.common.CountdownModelSource;
import edu.luc.etl.cs313.android.simplecountdown.common.CountdownUIListener;
import edu.luc.etl.cs313.android.simplecountdown.common.TimeInitializer;

/**
 * A thin model facade. Following the Facade pattern,
 * this isolates the complexity of the model from its clients (usually the adapter).
 *
 * @author laufer
 */
public interface CountdownModelFacade extends Startable, CountdownUIListener, CountdownModelSource, TimeInitializer { }
