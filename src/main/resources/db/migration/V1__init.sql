CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS activity (
                                        id SERIAL PRIMARY KEY,
                                        title VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    location VARCHAR(255),
    date_time TIMESTAMP,
    price NUMERIC(10,2),
    source VARCHAR(50),
    external_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE UNIQUE INDEX IF NOT EXISTS uniq_activity_source_ext ON activity(source, external_id);

CREATE TABLE IF NOT EXISTS weather_snapshot (
                                                id SERIAL PRIMARY KEY,
                                                location VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    tavg NUMERIC(5,2),
    tmin NUMERIC(5,2),
    tmax NUMERIC(5,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS air_quality (
                                           id SERIAL PRIMARY KEY,
                                           location VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    aqi INTEGER,
    pm25 NUMERIC(6,2),
    pm10 NUMERIC(6,2),
    no2 NUMERIC(6,2),
    o3 NUMERIC(6,2),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
    );

CREATE TABLE IF NOT EXISTS ingestion_log (
                                             id SERIAL PRIMARY KEY,
                                             source VARCHAR(50) NOT NULL,
    run_at TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(20) NOT NULL,
    message TEXT
    );
