drop procedure if exists WYKONAJ_CZYNNOSC;
drop procedure if exists ODLOZ_CZYNNOSC;
DELIMITER //

CREATE PROCEDURE WYKONAJ_CZYNNOSC (IN nazwa_czynnosci VARCHAR(180), IN data_wykonania DATE)
  BEGIN
    DECLARE data_nastepnego_sprzatania_param DATE;
    DECLARE data_ostatniego_sprzatania_param DATE;
    DECLARE czestotliwosc_param INT(6);

    SET data_ostatniego_sprzatania_param = (SELECT DATA_OSTATNIEGO_SPRZATANIA FROM SPRZATANIE_CZYNNOSC WHERE NAZWA = nazwa_czynnosci);
    SET czestotliwosc_param = (SELECT CZESTOTLIWOSC FROM SPRZATANIE_CZYNNOSC WHERE NAZWA = nazwa_czynnosci);
    SET data_nastepnego_sprzatania_param = DATE_ADD(data_wykonania, INTERVAL czestotliwosc_param DAY);

    UPDATE SPRZATANIE_CZYNNOSC
    SET
      DATA_OSTATNIEGO_SPRZATANIA = data_wykonania,
      DATA_NASTEPNEGO_SPRZATANIA = data_nastepnego_sprzatania_param
    WHERE NAZWA = nazwa_czynnosci;
  END //

CREATE PROCEDURE ODLOZ_CZYNNOSC (IN nazwa_czynnosci VARCHAR(180))
  BEGIN
    DECLARE data_nastepnego_sprzatania_stara_param DATE;
    DECLARE data_nastepnego_sprzatania_nowa_param DATE;

    SET data_nastepnego_sprzatania_stara_param = (SELECT DATA_NASTEPNEGO_SPRZATANIA FROM SPRZATANIE_CZYNNOSC WHERE NAZWA = nazwa_czynnosci);
    SET data_nastepnego_sprzatania_nowa_param = DATE_ADD(data_nastepnego_sprzatania_stara_param, INTERVAL 7 DAY);

    UPDATE SPRZATANIE_CZYNNOSC
    SET
      DATA_NASTEPNEGO_SPRZATANIA = data_nastepnego_sprzatania_nowa_param
    WHERE NAZWA = nazwa_czynnosci;
  END //

DELIMITER ;
