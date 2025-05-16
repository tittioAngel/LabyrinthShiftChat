package org.labyrinthShiftChat.model.tiles;

import org.labyrinthShiftChat.model.tiles.common.*;
import org.labyrinthShiftChat.model.tiles.enemy.*;
import org.labyrinthShiftChat.model.tiles.obstacle.*;
import org.labyrinthShiftChat.model.tiles.powerUp.*;

import java.util.Map;

public class MazeSymbolRegistry {
    private static final Map<Class<? extends MazeComponent>, Character> SYMBOL_MAP = Map.ofEntries(
            Map.entry(Wall.class, '#'),
            Map.entry(Corridor.class, '.'),
            Map.entry(StartTile.class, 'S'),
            Map.entry(ExitTile.class, 'E'),

            Map.entry(IceCyclops.class, 'N'),
            Map.entry(PhantomHorse.class, 'N'),
            Map.entry(ShadowDemon.class, 'N'),

            Map.entry(FreezingFog.class, 'O'),
            Map.entry(Slime.class, 'O'),
            Map.entry(Thorns.class, 'O'),
            Map.entry(TimeVortex.class, 'O'),

            Map.entry(Sprint.class, 'P'),
            Map.entry(SightOrb.class, 'P'),
            Map.entry(ObstacleNullifier.class, 'P')
    );

    private static final Map<Character, String> EMOJI_MAP = Map.of(
            'S', "🏁 ",
            'O', "🚧 ",
            'N', "💀 ",
            'E', "🚪 ",
            'G', "🧑‍ ",
            '#', "🧱 ",
            '.', "⬜ ",
            'P', "✨ "
    );

    public static char getSymbolForComponent(MazeComponent component) {
        Class<?> cls = component.getClass();
        while (cls != null) {
            Character symbol = SYMBOL_MAP.get(cls);
            if (symbol != null) return symbol;
            cls = cls.getSuperclass();
        }
        return '?';
    }

    public static String getEmojiForSymbol(char symbol) {
        return EMOJI_MAP.getOrDefault(symbol, symbol + " ");
    }
}
