-- ─────────────────────────────────────────────────────────────────────────────
-- players/src/main/resources/data.sql
-- All columns included: bid_history, session_passkeys, sold_price,
-- bought_by, access_token, token_expiry (required by updated Player.java)
-- ─────────────────────────────────────────────────────────────────────────────

INSERT INTO player (
    name, email, password, country, position, age, mobile,
    basic_remuneration, status,
    sold_price, bought_by,
    odi_runs, t20_runs, batting_average, wickets,
    access_token, token_expiry,
    bid_history, session_passkeys
) VALUES
      ('Virat Kohli',      'virat@india.com',   'pass123', 'India',        'Batsman',        35, '9876543210', 2000000.0, 'AVAILABLE', NULL, NULL, 13848, 4037, 58.67,  4,   NULL, NULL, '[]', '{}'),
      ('Steve Smith',      'steve@aus.com',     'pass123', 'Australia',    'Batsman',        34, '9876543211', 1500000.0, 'AVAILABLE', NULL, NULL,  5416, 1094, 43.54, 28,   NULL, NULL, '[]', '{}'),
      ('Kane Williamson',  'kane@nz.com',       'pass123', 'New Zealand',  'Batsman',        33, '9876543212', 1200000.0, 'AVAILABLE', NULL, NULL,  6810, 2570, 48.45, 37,   NULL, NULL, '[]', '{}'),
      ('Babar Azam',       'babar@pak.com',     'pass123', 'Pakistan',     'Batsman',        29, '9876543213', 1000000.0, 'AVAILABLE', NULL, NULL,  5729, 3698, 56.72,  0,   NULL, NULL, '[]', '{}'),
      ('Jasprit Bumrah',   'jasprit@india.com', 'pass123', 'India',        'Bowler',         30, '9876543214', 1800000.0, 'AVAILABLE', NULL, NULL,    18,    8,  8.50,149,   NULL, NULL, '[]', '{}'),
      ('Rashid Khan',      'rashid@afg.com',    'pass123', 'Afghanistan',  'All-Rounder',    25, '9876543215', 1400000.0, 'AVAILABLE', NULL, NULL,  1322,  403, 19.45,176,   NULL, NULL, '[]', '{}'),
      ('Ben Stokes',       'ben@eng.com',       'pass123', 'England',      'All-Rounder',    32, '9876543216', 2000000.0, 'AVAILABLE', NULL, NULL,  3462,  585, 40.21, 74,   NULL, NULL, '[]', '{}'),
      ('Glenn Maxwell',    'glenn@aus.com',     'pass123', 'Australia',    'All-Rounder',    35, '9876543217', 1600000.0, 'AVAILABLE', NULL, NULL,  3895, 2431, 35.40, 65,   NULL, NULL, '[]', '{}'),
      ('Quinton de Kock',  'quin@sa.com',       'pass123', 'South Africa', 'Wicket-Keeper',  31, '9876543218', 1100000.0, 'AVAILABLE', NULL, NULL,  6770, 2487, 45.74,  0,   NULL, NULL, '[]', '{}'),
      ('Trent Boult',      'trent@nz.com',      'pass123', 'New Zealand',  'Bowler',         34, '9876543219', 1300000.0, 'AVAILABLE', NULL, NULL,     5,    2,  7.20,211,   NULL, NULL, '[]', '{}'),
      ('Joe Root',         'joe@eng.com',       'pass123', 'England',      'Batsman',        33, '9876543220', 1400000.0, 'AVAILABLE', NULL, NULL,  6522,  893, 47.60, 26,   NULL, NULL, '[]', '{}'),
      ('Shaheen Afridi',   'shaheen@pak.com',   'pass123', 'Pakistan',     'Bowler',         23, '9876543221', 1200000.0, 'AVAILABLE', NULL, NULL,    22,   12, 10.10,104,   NULL, NULL, '[]', '{}'),
      ('Pat Cummins',      'pat@aus.com',       'pass123', 'Australia',    'Bowler',         30, '9876543222', 1900000.0, 'AVAILABLE', NULL, NULL,   230,   85, 15.30,141,   NULL, NULL, '[]', '{}'),
      ('Shakib Al Hasan',  'shakib@ban.com',    'pass123', 'Bangladesh',   'All-Rounder',    36, '9876543223', 1000000.0, 'AVAILABLE', NULL, NULL,  7570, 2382, 37.20,317,   NULL, NULL, '[]', '{}'),
      ('Mohammed Shami',   'shami@india.com',   'pass123', 'India',        'Bowler',         33, '9876543224', 1500000.0, 'AVAILABLE', NULL, NULL,    32,   10,  9.40,195,   NULL, NULL, '[]', '{}'),
      ('David Warner',     'david@aus.com',     'pass123', 'Australia',    'Batsman',        37, '9876543225', 1200000.0, 'AVAILABLE', NULL, NULL,  6932, 2894, 45.30,  1,   NULL, NULL, '[]', '{}'),
      ('Kagiso Rabada',    'kg@sa.com',         'pass123', 'South Africa', 'Bowler',         28, '9876543226', 1600000.0, 'AVAILABLE', NULL, NULL,    12,    5, 12.00,157,   NULL, NULL, '[]', '{}'),
      ('Rishabh Pant',     'pant@india.com',    'pass123', 'India',        'Wicket-Keeper',  26, '9876543227', 1700000.0, 'AVAILABLE', NULL, NULL,   865,  987, 34.60,  0,   NULL, NULL, '[]', '{}'),
      ('Mitchell Starc',   'mitch@aus.com',     'pass123', 'Australia',    'Bowler',         34, '9876543228', 1850000.0, 'AVAILABLE', NULL, NULL,    56,   20, 14.20,236,   NULL, NULL, '[]', '{}'),
      ('Hardik Pandya',    'hardik@india.com',  'pass123', 'India',        'All-Rounder',    30, '9876543229', 1500000.0, 'AVAILABLE', NULL, NULL,  1769, 1348, 34.02, 84,   NULL, NULL, '[]', '{}');