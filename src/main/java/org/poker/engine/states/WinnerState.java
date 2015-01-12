/*
 * Copyright (C) 2015 David Pérez Cabrera <dperezcabrera@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.poker.engine.states;

import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.poker.api.game.TexasHoldEmUtil.PlayerState;
import org.poker.engine.model.ModelContext;
import org.poker.engine.model.PlayerEntity;
import org.util.statemachine.IState;

/**
 *
 * @author David Pérez Cabrera <dperezcabrera@gmail.com>
 */
@ThreadSafe
public class WinnerState implements IState<ModelContext> {

    public static final String NAME = "Winner";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean execute(ModelContext model) {
        List<PlayerEntity> players = model.getPlayers();
        players.stream()
                .filter(p -> p.isActive() || p.getState() == PlayerState.ALL_IN)
                .findFirst()
                .get()
                .addChips(players
                        .stream()
                        .map(p -> p.getBet())
                        .reduce(0L, (a, b) -> a + b));
        return true;
    }
}