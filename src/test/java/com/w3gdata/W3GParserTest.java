package com.w3gdata;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.w3gdata.ReplaySubHeader.GameType;
import com.w3gdata.Version.VersionIdentifiers;
import java.time.Duration;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class W3gParserTest {

    private static final Logger logger = Logger.getLogger(W3gParserTest.class);

    private static final String REPLAY_SOURCE_FILE_NAME = "w3replayTestFile.w3g";

    private static final int EXPECTED_PLAYERS_NUMBER = 2;

    private static final String EXPECTED_PLAYER_NAME_1 = "ZEIKOOO";

    private static final String EXPECTED_PLAYER_NAME_2 = "adoveov";

    private static final String EXPECTED_GAME_MODE = "1vs1";

    private static final String EXPECTED_MAP = "w3arena__turtlerock__v3";

    private static Duration EXPECTED_MATCH_LENGTH = Duration.ofMillis((45*60 + 24) * 1000 + 150);

    private W3gInfo w3gInfo;
    private ByteSource replaySourceFile;
    private W3gParser parser;

    @Before
    public void setUp() throws Exception {
        URL resourceURL = Resources.getResource(REPLAY_SOURCE_FILE_NAME);
        replaySourceFile = Resources.asByteSource(resourceURL);
        parser = new W3gParser();
    }

    @Test
    public void testParseHeader() throws Exception {
        try {
            w3gInfo = parser.parse(replaySourceFile);
            ReplayHeader expectedReplayHeader = new ReplayHeader(1, (int)replaySourceFile.size(), 68, 1, 1);
            ReplaySubHeader expectedReplaySubHeader = new ReplaySubHeader(new Version(VersionIdentifiers.W3XP, 26, 6059),
                    GameType.MULTI_PLAYER,
                    EXPECTED_MATCH_LENGTH, 32);
            ReplayInformation expectedReplayInformation = new ReplayInformation(expectedReplayHeader,
                    expectedReplaySubHeader);

            PlayerRecord host = new PlayerRecord();
            host.playerId = 2;
            host.recordId = 0;
            host.name = EXPECTED_PLAYER_NAME_1;
            W3gInfo expectedData = new W3gInfo();
            expectedData.replayInformation = expectedReplayInformation;
            expectedData.host = host;
            assertThat(w3gInfo).isEqualTo(expectedData);
        } catch (ParseException e) {
            fail("Should never happen!");
        }
        assertNotNull(w3gInfo);
    }

    @Test
    public void testReadPlayersNumber() throws Exception {
        assertThat(w3gInfo.getPlayerRecords()).hasSize(EXPECTED_PLAYERS_NUMBER);
    }

    @Test
    public void testReadGameMode() throws Exception {
        assertThat(w3gInfo.getGameMode()).isEqualTo(EXPECTED_GAME_MODE);
    }

    @Test
    public void testReadMap() throws Exception {
        assertThat(w3gInfo.getMap()).isEqualTo(EXPECTED_MAP);
    }
}

