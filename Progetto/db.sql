create table amministratore(
	id Serial primary key,
	username varchar not null unique,
	password varchar not null,
	nome_agenzia varchar not null,
	id_responsabile bigint,
	constraint responsabile_fk foreign key(id_responsabile) references amministratore(id)
	on update cascade
	on delete cascade
	lll
);


create table agente_immobiliare(
	id Serial primary key,
	username varchar not null unique,
	password varchar not null,
	id_amministratore bigint,
	constraint amministratore_fk foreign key(id_amministratore) references amministratore(id)
	on update cascade
	on delete cascade
	
);


create table cliente(
	id Serial primary key,
	email varchar not null unique,
	password varchar not null,
	nome varchar not null,
	cognome varchar not null
);

create table indirizzo(
	id Serial primary key,
	regione varchar not null,
	provincia varchar(2) not null,
	citta varchar not null,
	cap varchar not null,
	via varchar not null,
	numero_civico int not null
);

create table immobile(
	id Serial primary key,
	descrizione varchar not null,
	dimensione_in_mq double precision not null,
	numero_stanze int not null,
	numero_piano int not null,
	classe_energetica varchar not null,
	ascensore boolean,
	portineria boolean,
	climatizzatore boolean,
	terrazza boolean,
	garage boolean,
	balcone boolean,
	giardino boolean,
	piscina boolean,
	numero_posti_auto int default 0,
	id_indirizzo bigint not null,
	id_agente_immobiliare bigint not null,
	data_caricamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	constraint indirizzo_fk foreign key(id_indirizzo) references indirizzo(id)
	on update cascade 
	on delete cascade,
	constraint agente_fk foreign key(id_agente_immobiliare) references agente_immobiliare(id)
	on update cascade 
	on delete cascade
	
);

CREATE TABLE vendita (
    id bigint primary key,
    prezzo DOUBLE PRECISION not null,
    data_disponibilita DATE not null,
    stato_proprieta VARCHAR not null,
    constraint immobile_vendita_fk foreign key (id) references immobile(id)
        on update cascade
        on delete cascade
);

CREATE TABLE affitto (
    id bigint primary key,
    canone_mensile DOUBLE PRECISION not null,
    deposito_cauzionale DOUBLE PRECISION not null,
    durata_contatto_anni INT default 1,
    spesa_condominale DOUBLE PRECISION not null,
    constraint immobile_affitto_fk foreign key (id) references immobile(id)
        on update cascade
        on delete cascade
);

create table foto(
	id Serial primary key,
	chiave_s3 varchar not null,
	id_immobile bigint not null,
	constraint immobile_foto_fk foreign key(id_immobile)references immobile(id)
	on update cascade
	on delete cascade
	
	
);

create table statistiche_immobile(
	id Serial primary key,
	numero_visite bigint default 0,
	numero_offerte bigint default 0,
	id_immobile bigint not null,
	constraint immobile_statistiche_fk foreign key(id_immobile)references immobile(id)
	on update cascade
	on delete cascade
);

create table visualizza_immobile(
	id Serial primary key,
	data_ultima_visita DATE not null,
	id_immobile bigint not null,
	id_cliente bigint not null,
	constraint immobile_visualiazza_fk foreign key(id_immobile)references immobile(id)
	on update cascade
	on delete cascade,
	constraint cliente_visualizza_fk foreign key(id_cliente)references cliente(id)
	on update cascade
	on delete cascade
);
