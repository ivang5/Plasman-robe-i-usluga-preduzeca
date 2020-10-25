import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class StavkaPredracunaModal extends Component {
  state = {
    pdvKategorije: [],
    proizvodi: [],
    grupeProizvoda: [],
    stavkeCenovnika: [],
    urlCenovnik: "http://localhost:8080/api/cenovnik/",
    urlPdvKategorije: "http://localhost:8080/api/pdvKategorije/",
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
    urlGrupeProizvoda: "http://localhost:8080/api/grupeProizvoda/",
    proizvodId: 1,
    kolicina: 0,
    cena: 0,
    pdv: 0,
    pdvBroj: 0,
    rabat: 0,
    rabatBroj: 0,
    iznos: 0,
  };

  // Preuzimanje svih proizvoda sa servera
  async getProizvodi() {
    try {
      const data = await fetch(this.state.urlProizvodi, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan proizvod" };
        });
      } else {
        this.setState(() => {
          return { proizvodi: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih PDV kategorija sa servera
  async getPdvKategorije() {
    try {
      const data = await fetch(this.state.urlPdvKategorije, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna PDV kategorija" };
        });
      } else {
        this.setState(() => {
          return { pdvKategorije: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih grupa proizvoda sa servera
  async getGrupe() {
    try {
      const data = await fetch(this.state.urlGrupeProizvoda, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna grupa proizvoda" };
        });
      } else {
        this.setState(() => {
          return { grupeProizvoda: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  getPdvKategorija = (id) => {
    const pdvKategorija = this.state.pdvKategorije.find(
      // eslint-disable-next-line
      (element) => element.id == id
    );
    return pdvKategorija;
  };

  getGrupa = (id) => {
    const grupaProizvoda = this.state.grupeProizvoda.find(
      // eslint-disable-next-line
      (element) => element.id == id
    );
    return grupaProizvoda;
  };

  getProizvod = (id) => {
    // eslint-disable-next-line
    const proizvod = this.state.proizvodi.find((element) => element.id == id);
    return proizvod;
  };

  handleProizvodChange = (e) => {
    const tempProizvod = this.getProizvod(e.target.value);
    const tempGrupaProizvoda = this.getGrupa(tempProizvod.idGrupe);
    const tempPdvKategorija = this.getPdvKategorija(
      tempGrupaProizvoda.idPdvKategorije
    );
    this.setState(
      {
        proizvodId: e.target.value,
        pdv: tempPdvKategorija.stopePDV[0].procenat,
      },
      () => {
        const cena = this.getCenaStavke(this.state.proizvodId);
        document.getElementById("cenaInput").value = cena;
        const pocetniIznos = this.state.kolicina * cena;
        const rabat = (pocetniIznos / 100) * this.state.rabat;
        const iznosBezPdv = pocetniIznos - rabat;
        const pdv = (iznosBezPdv / 100) * this.state.pdv;
        const iznos = iznosBezPdv + pdv;
        this.setState({
          cena: cena,
          iznos: iznos,
        });
        document.getElementById("iznosInput").value = iznos;
      }
    );
  };

  handleKolicinaChange = (e) => {
    this.setState(
      {
        kolicina: e.target.value,
      },
      () => {
        this.handleIznosChange();
      }
    );
  };

  handleCenaChange = (e) => {
    this.setState(
      {
        cena: e.target.value,
      },
      () => {
        this.handleIznosChange();
      }
    );
  };

  handleRabatChange = (e) => {
    this.setState(
      {
        rabat: e.target.value,
      },
      () => {
        this.handleIznosChange();
      }
    );
  };

  handleIznosChange = () => {
    const pocetniIznos = this.state.kolicina * this.state.cena;
    const rabat = (pocetniIznos / 100) * this.state.rabat;
    const iznosBezPdv = pocetniIznos - rabat;
    const pdv = (iznosBezPdv / 100) * this.state.pdv;
    const iznos = iznosBezPdv + pdv;
    this.setState({
      rabatBroj: rabat,
      pdvBroj: pdv,
      iznos: iznos,
    });
    document.getElementById("iznosInput").value =
      iznos < 0 ? 0 : (Math.round(iznos * 100) / 100).toFixed(2);
  };

  // Preuzimanje svih stavki najnovijeg cenovnika sa servera
  async getStavkeCenovnika() {
    try {
      const data = await fetch(this.state.urlCenovnik, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonDataCenovnici = await data.json();

      if (jsonDataCenovnici.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan cenovnik" };
        });
      } else {
        const newCenovnik = jsonDataCenovnici[0];
        this.setState(() => {
          return { stavkeCenovnika: newCenovnik.stavkeCenovnika };
        });
        this.setState({
          cena: this.getCenaStavke(1),
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  componentDidMount() {
    this.getProizvodi();
    this.getGrupe();
    this.getPdvKategorije();
    this.getStavkeCenovnika();
  }

  getCenaStavke = (id) => {
    const stavka = this.state.stavkeCenovnika.find(
      // eslint-disable-next-line
      (item) => item.proizvod.id == id
    );
    if (stavka === undefined) {
      return 0;
    } else {
      const cenaStavke = stavka.cena;
      return cenaStavke;
    }
  };

  resetIznos = () => {
    this.setState({
      iznos: 0,
    });
  };

  UNSAFE_componentWillReceiveProps() {
    this.clearState();
  }

  clearState = () => {
    this.setState({
      kolicina: 0,
      cena: this.getCenaStavke(1),
      pdv: 0,
      rabat: 0,
    });
  };

  render() {
    const { modalOpen, saveModal, closeModal, proizvodi } = this.props;
    if (!modalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-4 text-center text-capitalize p-5"
              >
                <h3>Stavka predračuna</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  proizvod/usluga: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    onChange={this.handleProizvodChange}
                  >
                    {proizvodi.map((item) => (
                      <option key={item.id} value={item.id}>
                        {item.naziv}
                      </option>
                    ))}
                  </select>
                </h5>
                <h5 className="mb-3">
                  količina: <br />{" "}
                  <input
                    type="number"
                    className="form-control"
                    defaultValue="0"
                    onChange={this.handleKolicinaChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  cena: <br />{" "}
                  <input
                    id="cenaInput"
                    type="number"
                    className="form-control"
                    defaultValue={this.getCenaStavke(1)}
                    onChange={this.handleCenaChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  PDV (%): <br />{" "}
                  <input
                    type="number"
                    className="form-control"
                    value={this.state.pdv}
                    readOnly
                  ></input>
                </h5>
                <h5 className="mb-3">
                  rabat (%): <br />{" "}
                  <input
                    type="number"
                    className="form-control"
                    defaultValue="0"
                    onChange={this.handleRabatChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  iznos: <br />{" "}
                  <input
                    id="iznosInput"
                    type="number"
                    className="form-control"
                    defaultValue="0.00"
                    readOnly
                  ></input>
                </h5>
                <Link to="/izdavanje-predracuna">
                  <button
                    className="btn btn-success m-4"
                    onClick={() =>
                      saveModal(
                        this.state.proizvodId,
                        this.state.kolicina,
                        this.state.cena,
                        this.state.pdvBroj,
                        this.state.rabatBroj,
                        this.state.iznos
                      )
                    }
                  >
                    Dodaj
                  </button>
                </Link>
                <Link to="/izdavanje-predracuna">
                  <button className="btn btn-danger m-4" onClick={closeModal}>
                    Odustani
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </ModalContainer>
      );
    }
  }
}

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  #modal {
    background: var(--mainWhite);
  }
`;
