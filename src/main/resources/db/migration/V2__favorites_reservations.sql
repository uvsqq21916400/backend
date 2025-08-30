create table if not exists user_favorites (
                                              id bigserial primary key,
                                              user_id varchar(100) not null,
    event_uid varchar(50) not null,
    snapshot jsonb not null,
    created_at timestamptz not null default now(),
    unique (user_id, event_uid)
    );

create table if not exists user_reservations (
                                                 id bigserial primary key,
                                                 user_id varchar(100) not null,
    event_uid varchar(50) not null,
    timing_begin timestamptz,
    price_cents int,
    status varchar(20) not null default 'CONFIRMED',
    snapshot jsonb not null,
    created_at timestamptz not null default now()
    );

create index if not exists idx_fav_user on user_favorites(user_id);
create index if not exists idx_res_user on user_reservations(user_id);
