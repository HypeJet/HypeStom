package org.labgames.nextlib.location;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.Instance;

public record Location(Instance instance, Pos pos) {}
