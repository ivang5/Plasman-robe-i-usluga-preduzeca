import React, { Component } from "react";
import styled from "styled-components";
import IzmenaModal from "./IzmenaModal";
import AddRacunModal from "./AddRacunModal";
import DeleteRacunModal from "./DeleteRacunModal";
import { Redirect } from "react-router-dom";

export default class Informacije extends Component {
  state = {
    preduzece: {},
    zaposleni: [],
    racuniUBanci: "",
    generalniDirektor: "Trenutno ne postoji",
    direktorFinansija: "Trenutno ne postoji",
    direktorMarketinga: "Trenutno ne postoji",
    rukovodilacPostrojenja: "Trenutno ne postoji",
    operateri: "Trenutno ne postoje",
    urlPreduzece: "http://localhost:8080/api/preduzece/",
    modalOpen: false,
    modalAddRacun: false,
    modalDeleteRacun: false,
    login: null,
  };

  // Preuzimanje preduzeca i zaposlenih sa servera
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
          return {
            preduzece: jsonData,
            zaposleni: jsonData.zaposleni,
          };
        });
        this.getZaposleni();
        this.getRacuniUBanci();
      }
    } catch (error) {
      console.log(error);
    }
  }

  getZaposleni = () => {
    const generalniDirektor = this.state.zaposleni.find(
      (item) => item.uloga === "GENERALNI_DIREKTOR"
    );
    if (generalniDirektor !== undefined) {
      this.setState({
        generalniDirektor:
          generalniDirektor.ime + " " + generalniDirektor.prezime,
      });
    }
    const direktorFinansija = this.state.zaposleni.find(
      (item) => item.uloga === "DIREKTOR_FINANSIJA"
    );
    if (direktorFinansija !== undefined) {
      this.setState({
        direktorFinansija:
          direktorFinansija.ime + " " + direktorFinansija.prezime,
      });
    }
    const direktorMarketinga = this.state.zaposleni.find(
      (item) => item.uloga === "DIREKTOR_MARKETINGA"
    );
    if (direktorMarketinga !== undefined) {
      this.setState({
        direktorMarketinga:
          direktorMarketinga.ime + " " + direktorMarketinga.prezime,
      });
    }
    const rukovodilacPostrojenja = this.state.zaposleni.find(
      (item) => item.uloga === "RUKOVODILAC_POSTROJENJA"
    );
    if (rukovodilacPostrojenja !== undefined) {
      this.setState({
        rukovodilacPostrojenja:
          rukovodilacPostrojenja.ime + " " + rukovodilacPostrojenja.prezime,
      });
    }
    const operateri = [];
    this.state.zaposleni.forEach((element) => {
      if (element.uloga === "OPERATER") {
        operateri.push(element.ime + " " + element.prezime);
      }
    });
    if (operateri.length > 0) {
      this.setState({
        operateri: operateri,
      });
    }
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
    this.getPreduzece();
  }

  openModal = (e) => {
    e.preventDefault();
    this.setState({
      modalOpen: true,
    });
  };

  addRacunModal = (e) => {
    e.preventDefault();
    this.setState({
      modalAddRacun: true,
    });
  };

  deleteRacunModal = (e) => {
    e.preventDefault();
    this.setState({
      modalDeleteRacun: true,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
      modalAddRacun: false,
      modalDeleteRacun: false,
    });
  };

  saveModal = (naziv, pib, telefon) => {
    const nazivConst = naziv === "" ? this.state.preduzece.naziv : naziv;
    const pibConst = pib === 0 ? this.state.preduzece.pib : pib;
    const telefonConst =
      telefon === "" ? this.state.preduzece.telefon : telefon;

    const data = {
      id: this.state.preduzece.id,
      naziv: nazivConst,
      pib: pibConst,
      telefon: telefonConst,
    };
    fetch(this.state.urlPreduzece, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token"),
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.closeModal();
        this.getPreduzece();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  getRacuniUBanci = () => {
    var racuni = "";
    if (this.state.preduzece.racuniUBanci !== undefined) {
      this.state.preduzece.racuniUBanci.forEach((element) => {
        racuni =
          racuni === ""
            ? element.nazivBanke + "(" + element.brojRacuna + ")"
            : racuni +
              ", " +
              element.nazivBanke +
              "(" +
              element.brojRacuna +
              ")";
      });
      this.setState({
        racuniUBanci: racuni,
      });
    }
  };

  addRacun = (nazivBanke, brojRacuna) => {
    const data = {
      brojRacuna: brojRacuna,
      nazivBanke: nazivBanke,
      obrisan: false,
    };
    fetch(this.state.urlPreduzece + "addRacun", {
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
        this.getPreduzece();
        this.closeModal();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  deleteRacun = (id) => {
    fetch(this.state.urlPreduzece + "deleteRacun/" + id, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token"),
      },
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.getPreduzece();
        this.closeModal();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    return (
      <React.Fragment>
        <ContainerWrapper className="container-flex">
          <div className="d-flex justify-content-center h-100">
            <div className="card">
              <div className="card-header">
                <h2>Informacije o preduzeću</h2>
              </div>
              <div className="card-body">
                <form>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Naziv:</label>
                    <input
                      type="text"
                      className="form-control margin-left-1"
                      defaultValue={this.state.preduzece.naziv}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">PIB:</label>
                    <input
                      type="number"
                      className="form-control margin-left-2"
                      defaultValue={this.state.preduzece.pib}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Telefon:</label>
                    <input
                      type="text"
                      className="form-control margin-left-3"
                      defaultValue={this.state.preduzece.telefon}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Racuni u banci:</label>
                    <textarea
                      type="text"
                      className="form-control margin-left-8 max-height-6"
                      defaultValue={this.state.racuniUBanci}
                      readOnly
                    />
                    <button
                      className="btn-add ml-2"
                      onClick={this.addRacunModal}
                    >
                      <i className="fas fa-plus" />
                    </button>
                    <button
                      className="btn-clear ml-2"
                      onClick={this.deleteRacunModal}
                    >
                      <i className="fas fa-pen" />
                    </button>
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">
                      Generalni direktor:
                    </label>
                    <input
                      type="text"
                      className="form-control margin-left-4"
                      value={this.state.generalniDirektor}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">
                      Direktor finansija:
                    </label>
                    <input
                      type="text"
                      className="form-control margin-left-5"
                      value={this.state.direktorFinansija}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">
                      Direktor marketinga:
                    </label>
                    <input
                      type="text"
                      className="form-control margin-left-6"
                      value={this.state.direktorMarketinga}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">
                      Rukovodilac postrojenja:
                    </label>
                    <input
                      type="text"
                      className="form-control"
                      value={this.state.rukovodilacPostrojenja}
                      readOnly
                    />
                  </div>
                  <div className="input-group form-group">
                    <label className="card-label mr-3">Operateri:</label>
                    <textarea
                      type="text"
                      className="form-control margin-left-7"
                      value={this.state.operateri}
                      readOnly
                    />
                  </div>
                  <hr />
                  <div className="form-group">
                    <input
                      type="submit"
                      value="Promeni"
                      className="btn float-right btn-submit"
                      onClick={this.openModal}
                    />
                  </div>
                </form>
              </div>
            </div>
          </div>
        </ContainerWrapper>
        <IzmenaModal
          preduzece={this.state.preduzece}
          modalOpen={this.state.modalOpen}
          saveModal={this.saveModal}
          closeModal={this.closeModal}
        />
        <AddRacunModal
          modalAddRacun={this.state.modalAddRacun}
          addRacun={this.addRacun}
          closeModal={this.closeModal}
        />
        <DeleteRacunModal
          racuni={this.state.preduzece.racuniUBanci}
          modalDeleteRacun={this.state.modalDeleteRacun}
          deleteRacun={this.deleteRacun}
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
  background-image: url("https://s1.1zoom.me/b4651/893/Wheat_Fields_Agricultural_machinery_556816_2560x1440.jpg");
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  .card {
    height: 50.5rem;
    margin-top: 3rem;
    width: 60rem;
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
    background-color: var(--mainOrange);
    border-radius: 50%;
  }
  .btn-clear: hover {
    background-color: var(--darkOrange);
  }
  .margin-left-1 {
    margin-left: 12.07rem;
  }
  .margin-left-2 {
    margin-left: 13.67rem;
  }
  }
  .margin-left-3 {
    margin-left: 11.05rem;
  }
  .margin-left-4 {
    margin-left: 3.92rem;
  }
  }
  .margin-left-5 {
    margin-left: 4.65rem;
  }
  .margin-left-6 {
    margin-left: 2.6rem;
  }
  }
  .margin-left-7 {
    margin-left: 9.63rem;
  }
  .margin-left-8 {
    margin-left: 6.18rem;
  }
  .max-height-6 {
    min-height: 2.6rem;
    max-height: 6rem;
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
