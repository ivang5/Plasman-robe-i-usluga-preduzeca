import React, { Component } from "react";
import styled from "styled-components";
import StavkaPredracunaModal from "./StavkaPredracunaModal";
import { Redirect } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default class IzdavanjePredracuna extends Component {
  state = {
    poslovniPartneri: [],
    proizvodi: [],
    stavkeCenovnika: [],
    stavkePredracuna: [],
    racuni: [],
    kupac: {},
    pozivNaBroj: "",
    racun: 0,
    ukupanIznos: 0,
    ukupanRabat: 0,
    pdv: 0,
    rokIsporuke: new Date(),
    rokPlacanja: new Date(),
    urlPP: "http://localhost:8080/api/poslovniPartneri/",
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
    urlPreduzece: "http://localhost:8080/api/preduzece/",
    urlPredracuna: "http://localhost:8080/api/predracuni/",
    modalOpen: false,
    error: "",
    redirect: null,
    login: null,
    brojStavki: 0,
  };

  // Preuzimanje svih poslovnih partnera sa servera
  async getPoslovniPartneri() {
    try {
      const data = await fetch(this.state.urlPP, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan poslovni partner" };
        });
      } else {
        this.setState(() => {
          return { poslovniPartneri: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

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

  // Preuzimanje preduzeca i njegovih racuna sa servera
  async getPreduzece() {
    try {
      const data = await fetch(this.state.urlPreduzece, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData === null) {
        this.setState(() => {
          return { error: "Greska, nije pronadjeno preduzece" };
        });
      } else {
        this.setState(() => {
          return { racuni: jsonData.racuniUBanci };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  handleRokIsporukeChange = (e) => {
    this.setState({
      rokIsporuke: e,
    });
  };

  handleRokPlacanjaChange = (e) => {
    this.setState({
      rokPlacanja: e,
    });
  };

  handlePPChange = (e) => {
    const poslovniPartner = this.getPoslovniPartner(e.target.value);
    this.setState({
      kupac: poslovniPartner,
    });
  };

  handleRacunChange = (e) => {
    this.setState({
      racun: e.target.value,
    });
  };

  componentDidMount() {
    if (
      sessionStorage.getItem("token") === null ||
      sessionStorage.getItem("token") === undefined
    ) {
      this.setState({
        login: "/",
      });
    }
    this.getPoslovniPartneri();
    this.getProizvodi();
    this.getPreduzece();
  }

  openModal = (e) => {
    e.preventDefault();
    if (this.state.brojStavki < 7) {
      this.setState({
        modalOpen: true,
      });
    } else {
      alert(
        "Predračun poseduje maksimalan broj stavki, potrebno je napraviti novi predračun ukoliko ima još stavki za dodavanje"
      );
    }
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
    });
  };

  saveModal = (proizvodId, kolicina, cena, pdv, rabat, iznos) => {
    if (iznos === 0) {
      alert("Niste popunili sva potrebna polja!");
    } else {
      const proizvod = this.getProizvod(proizvodId);
      const stavka = {
        proizvod: proizvod,
        kolicina: kolicina,
        cena: cena,
        pdv: pdv,
        rabat: rabat,
        iznos: iznos,
      };
      let tempList = [...this.state.stavkePredracuna, stavka];
      let tempUkupanIznos = this.state.ukupanIznos + iznos;
      let tempUkupanRabat = this.state.ukupanRabat + rabat;
      this.setState({
        ukupanIznos: tempUkupanIznos,
        ukupanRabat: tempUkupanRabat,
        pdv: pdv,
        stavkePredracuna: tempList,
        modalOpen: false,
        brojStavki: this.state.brojStavki + 1,
      });
      let tempStavkeInput =
        document.getElementById("stavkeInput").value +
        ", " +
        proizvod.naziv +
        "(" +
        kolicina +
        proizvod.jedinicaMere +
        ")";
      document.getElementById("stavkeInput").value === ""
        ? (document.getElementById("stavkeInput").value =
            proizvod.naziv + "(" + kolicina + proizvod.jedinicaMere + ")")
        : (document.getElementById("stavkeInput").value = tempStavkeInput);
    }
  };

  getProizvod = (id) => {
    // eslint-disable-next-line
    const proizvod = this.state.proizvodi.find((item) => item.id == id);
    return proizvod;
  };

  getPoslovniPartner = (id) => {
    const poslovniPartner = this.state.poslovniPartneri.find(
      // eslint-disable-next-line
      (item) => item.id == id
    );
    return poslovniPartner;
  };

  clearStavke = (e) => {
    e.preventDefault();
    this.setState({
      stavkePredracuna: [],
      brojStavki: 0,
    });
    document.getElementById("stavkeInput").value = "";
  };

  izdavanje = (e) => {
    e.preventDefault();
    if (
      this.state.stavkePredracuna.length < 1 ||
      Object.keys(this.state.kupac).length === 0
    ) {
      alert("Niste popunili sva potrebna polja!");
    } else {
      var today = new Date();
      var year = today.getFullYear();
      var racun =
        this.state.racun === 0
          ? document.getElementById("racunInput").value
          : this.state.racun;
      const data = {
        idIzdavaoca: JSON.parse(sessionStorage.getItem("podaci")).id,
        stavkePredracuna: this.state.stavkePredracuna,
        rokIsporuke: this.state.rokIsporuke,
        rokPlacanja: this.state.rokPlacanja,
        brojRacuna: racun,
        iznos: this.state.ukupanIznos,
        pdv: this.state.pdv,
        ukupanRabat: this.state.ukupanRabat,
        kupac: this.state.kupac,
        poslovnaGodina: year,
      };
      fetch(this.state.urlPredracuna, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": sessionStorage.getItem("token"),
        },
        body: JSON.stringify(data),
      })
        .then((res) => {
          res.text();
          console.log(res);
          alert("Predračun uspešno izdat!");
          this.setState({
            brojStavki: 0,
            redirect: "/knjiga-predracuna",
          });
        })
        .catch((error) => {
          console.error("Error: ", error);
        });
    }
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    } else if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    return (
      <React.Fragment>
        <ContainerWrapper className="container-flex">
          <div className="d-flex justify-content-center h-100">
            <div className="card">
              <div className="card-header">
                <h2>Izdavanje predračuna</h2>
              </div>
              <div className="card-body">
                <form>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Kupac:</label>
                    <select
                      id="ppInput"
                      type="text"
                      className="form-control margin-left-1"
                      onChange={this.handlePPChange}
                    >
                      <option disabled selected value>
                        {" "}
                        -- izaberite kupca --{" "}
                      </option>
                      {this.state.poslovniPartneri.map((item) => (
                        <option key={item.id} value={item.id}>
                          {item.naziv}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Rok isporuke:</label>
                    <DatePicker
                      className="form-control date-width"
                      selected={this.state.rokIsporuke}
                      onChange={this.handleRokIsporukeChange}
                      name="rokIsporuke"
                      dateFormat="dd/MM/yyyy"
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Rok plaćanja:</label>
                    <DatePicker
                      className="form-control margin-left-3"
                      selected={this.state.rokPlacanja}
                      onChange={this.handleRokPlacanjaChange}
                      name="rokPlacanja"
                      dateFormat="dd/MM/yyyy"
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Račun:</label>
                    <select
                      id="racunInput"
                      type="text"
                      className="form-control margin-left-1"
                      onChange={this.handleRacunChange}
                    >
                      {this.state.racuni.map((item) => (
                        <option key={item.id} value={item.brojRacuna}>
                          {item.brojRacuna}
                        </option>
                      ))}
                    </select>
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Stavke:</label>
                    <input
                      id="stavkeInput"
                      type="text"
                      className="form-control margin-left-2"
                      readOnly
                    />
                    <button className="btn-add ml-2" onClick={this.openModal}>
                      <i className="fas fa-plus" />
                    </button>
                    <button
                      className="btn-clear ml-2"
                      onClick={this.clearStavke}
                    >
                      <i className="fas fa-trash" />
                    </button>
                  </div>
                  <hr />
                  <div className="form-group">
                    <input
                      type="submit"
                      value="Potvrdi"
                      className="btn float-right btn-submit"
                      onClick={this.izdavanje}
                    />
                  </div>
                </form>
              </div>
            </div>
          </div>
        </ContainerWrapper>
        <StavkaPredracunaModal
          proizvodi={this.state.proizvodi}
          modalOpen={this.state.modalOpen}
          saveModal={this.saveModal}
          closeModal={this.closeModal}
        />
      </React.Fragment>
    );
  }
}

const ContainerWrapper = styled.div`
  margin-top: 3.94rem;
  height: 100%;
  align-content: center;
  background-image: url("https://i.imgur.com/vX0fEHj.jpg");
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  .card {
    height: 32rem;
    margin-top: 12rem;
    width: 68rem;
    background-color: rgba(0, 0, 0, 0.8) !important;
  }
  .card-header {
    color: var(--mainWhite);
    height: 4.5rem;
  }
  .card-label {
    color: var(--mainWhite);
    font-size: 1.5rem;
  }
  .margin-left-1 {
    margin-left: 4.5rem;
  }
  .margin-left-2 {
    margin-left: 4.32rem;
  }
  .margin-left-3 {
    margin-left: 0.2rem;
    width: 55.5rem;
  }
  .date-width {
    width: 55.5rem;
  }
  .btn-add {
    color: var(--mainDark);
    width: 2.6rem;
    height: 2.6rem;
    background-color: var(--lightGreen);
    border-radius: 50%;
  }
  .btn-add: hover {
    background-color: var(--mainGreen);
  }
  .btn-clear {
    color: var(--mainDark);
    width: 2.6rem;
    height: 2.6rem;
    background-color: var(--lightRed);
    border-radius: 50%;
  }
  .btn-clear: hover {
    background-color: var(--mediumRed);
  }
  .btn-submit {
    margin-top: 1.4rem;
    color: black;
    background-color: var(--mainOrange);
    width: 6rem;
    height: 2.7rem;
  }
  .btn-submit: hover {
    background-color: var(--lightOrange);
  }
  hr {
    margin-top: 1.5rem;
    background-color: var(--mainWhite);
  }
`;
