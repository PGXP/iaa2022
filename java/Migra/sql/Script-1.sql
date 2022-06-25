select ano, mes, dia, semana, semanaano, diaano, mercado, produto, valor from grupo15 g 
where produto = 'Alface 1 UN' and valor is not null
order by ano, mes, dia, mercado

select count(*) from grupo15 g where produto = 'Alface 1 UN' and valor is null;

select ano, mes, dia, semana, semanaano, diaano, valor from dolar d 
where "data" >= '2010-05-01' and "data" <= '2020-04-29'
order by ano , mes, dia  

05/01/2010 -> 29/04/2020

drop table public.produto270;
CREATE TABLE public.produto270 (
	id serial4 NOT NULL,
	ano int2 NOT NULL,
	mes int2 NOT NULL,
	dia int2 NOT NULL,
	semana int4 NULL,
	semanaano int4 NULL,
	diaano int4 NULL,
	mercado varchar(512) NULL,
	bairro varchar(512) NULL,
	produto varchar(512) NULL,
	valor float8 NULL,
	status varchar(32) NULL,
	CONSTRAINT produto270_pkey PRIMARY KEY (id)
);
CREATE INDEX produto270_produto_idx ON public.produto270 USING btree (produto, mercado, bairro, ano, mes, dia);
CREATE UNIQUE INDEX produto270_uniq_idx ON public.produto270 USING btree (ano, mes, dia, mercado, bairro, produto);

-- Auto-generated SQL script #202206251524
UPDATE public.locals
	SET nome='BOM_GOSTO FAZENDINHA'
	WHERE id=236;
UPDATE public.locals
	SET nome='CASA_FIESTA ALTO DA RUA XV'
	WHERE id=38;
UPDATE public.locals
	SET nome='CASA_FIESTA JARDIM DAS AMERICAS'
	WHERE id=41;
UPDATE public.locals
	SET nome='COMPRE_BEM_RAPOSO PILARZINHO'
	WHERE id=180;
UPDATE public.locals
	SET nome='DU_LEO SANTA CANDIDA'
	WHERE id=155;
UPDATE public.locals
	SET nome='MINI_PREÇO CAJURU'
	WHERE id=229;
UPDATE public.locals
	SET nome='PAO_DE_ACUCAR AGUA VERDE'
	WHERE id=25;
UPDATE public.locals
	SET nome='PAO_DE_ACUCAR BIGORRILHO'
	WHERE id=26;
UPDATE public.locals
	SET nome='REDE_MASTER CACHOEIRA'
	WHERE id=174;
UPDATE public.locals
	SET nome='RIO_VERDE BAIRRO ALTO'
	WHERE id=133;
UPDATE public.locals
	SET nome='RIO_VERDE TARUMA'
	WHERE id=168;
UPDATE public.locals
	SET nome='RIO_VERDE Xingu BAIRRO ALTO'
	WHERE id=203;
UPDATE public.locals
	SET nome='SUPER_10 CAJURU'
	WHERE id=181;
UPDATE public.locals
	SET nome='SUPER_CRISTAL CAPÃO DA IMBUIA'
	WHERE id=144;
UPDATE public.locals
	SET nome='SUPER_DIP CENTRO CIVICO'
	WHERE id=27;
UPDATE public.locals
	SET nome='SUPERMERCADO_CURITIBA CIDADE INDUSTRIAL'
	WHERE id=235;
UPDATE public.locals
	SET nome='TELEMACO_BORBA PORTAO'
	WHERE id=232;
UPDATE public.locals
	SET nome='TOK _SUPER CAPÃO DA IMBUIA'
	WHERE id=146;
UPDATE public.locals
	SET nome='VERDE_MAIS HAUER'
	WHERE id=179;
UPDATE public.locals
	SET nome='VIDEIRA_VILA_SÃO_PEDRO XAXIM'
	WHERE id=152;


