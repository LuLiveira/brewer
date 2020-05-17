CREATE TABLE estilo (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cerveja (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	sku VARCHAR(50) NOT NULL,
	nome VARCHAR(50) NOT NULL,
	descricao TEXT,
	valor DECIMAL(10, 2) NOT NULL,
	teor_alcoolico DECIMAL(10, 2) NOT NULL,
	comissao DECIMAL(10, 2) NOT NULL,
	sabor VARCHAR(50) NOT NULL,
	origem VARCHAR(50) NOT NULL,
	id_estilo BIGINT(20) NOT NULL,
	FOREIGN KEY (id_estilo) REFERENCES estilo(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO estilo (nome) values ('Amber Lager');
INSERT INTO estilo (nome) values ('Dark Lager');
INSERT INTO estilo (nome) values ('Pale Lager');
INSERT INTO estilo (nome) values ('Pilsner');