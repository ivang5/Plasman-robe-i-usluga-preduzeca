import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class FakturaModal extends Component {
  state = {
    zaposleni: [],
    urlZaposleni: "http://localhost:8080/api/zaposleni/",
    error: "",
  };

  // Preuzimanje svih zaposlenih sa servera
  async getZaposleni() {
    try {
      const data = await fetch(this.state.urlZaposleni, {
        headers: {
          "X-Auth-Token": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan zaposleni" };
        });
      } else {
        this.setState(() => {
          return { zaposleni: jsonData };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  componentDidMount() {
    this.getZaposleni();
  }

  prikazStavki = (stavkeFakture) => {
    var stavkeString = "";
    stavkeFakture.forEach((element) => {
      if (stavkeString === "") {
        stavkeString +=
          element.proizvod.naziv +
          "(" +
          element.kolicina +
          element.proizvod.jedinicaMere +
          ")";
      } else {
        stavkeString +=
          ", " +
          element.proizvod.naziv +
          "(" +
          element.kolicina +
          element.proizvod.jedinicaMere +
          ")";
      }
    });
    return stavkeString;
  };

  getIzdavaoc = (id) => {
    const zaposleni = this.state.zaposleni.find((element) => element.id === id);
    return zaposleni.ime + " " + zaposleni.prezime;
  };

  formatDate = (date) => {
    var today = new Date(date);
    var formattedDate =
      today.getDate() +
      "/" +
      (today.getMonth() + 1) +
      "/" +
      today.getFullYear();
    return formattedDate;
  };

  render() {
    const { modalOpen, closeModal, generateRacunOtpremnica } = this.props;
    const {
      idIdavaoca,
      stavkeFakture,
      datumFakture,
      brojRacuna,
      iznos,
      pdv,
      ukupanRabat,
      status,
      kupac,
      pozivNaBroj,
      poslovnaGodina,
    } = this.props.modalFaktura;
    if (!modalOpen) {
      return null;
    } else {
      let stavkeString = this.prikazStavki(stavkeFakture);
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-10 mx-auto col-md-8 text-center text-capitalize p-5"
              >
                <h3>Faktura</h3>
                <hr />
                <br />
                <div className="row">
                  <div className="col-6">
                    <h5 className="mb-3">
                      izdavaoc: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={this.getIzdavaoc(idIdavaoca)}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                  <div className="col-6">
                    <h5 className="mb-3">
                      kupac: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={kupac.naziv}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                </div>
                <h5 className="mb-3">
                  stavke: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    value={stavkeString}
                    readOnly
                  ></input>
                </h5>
                <h5 className="mb-3">
                  poziv na broj: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    value={pozivNaBroj}
                    readOnly
                  ></input>
                </h5>
                <h5 className="mb-3">
                  broj računa: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    value={brojRacuna}
                    readOnly
                  ></input>
                </h5>
                <div className="row">
                  <div className="col-4">
                    <h5 className="mb-3">
                      iznos: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={iznos}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                  <div className="col-4">
                    <h5 className="mb-3">
                      PDV: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={pdv}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                  <div className="col-4">
                    <h5 className="mb-3">
                      ukupan rabat: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={ukupanRabat}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                </div>
                <div className="row">
                  <div className="col-4">
                    <h5 className="mb-3">
                      status: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={status}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                  <div className="col-4">
                    <h5 className="mb-3">
                      poslovna godina: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={poslovnaGodina}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                  <div className="col-4">
                    <h5 className="mb-3">
                      datum: <br />{" "}
                      <input
                        type="text"
                        className="form-control"
                        value={this.formatDate(datumFakture)}
                        readOnly
                      ></input>
                    </h5>
                  </div>
                </div>

                {/* <Link to="/knjiga-faktura">
                  <button className="btn btn-success my-btn mx-3">
                    Štampaj
                  </button>
                </Link> */}
                <Link to="/knjiga-faktura">
                  <button
                    className="btn btn-primary my-btn-wide mx-3"
                    onClick={generateRacunOtpremnica}
                  >
                    Preuzmi račun-otpremnicu
                  </button>
                </Link>
                <Link to="/knjiga-faktura">
                  <button
                    className="btn btn-danger my-btn mx-3"
                    onClick={closeModal}
                  >
                    Izađi
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
  .my-btn {
    width: 6rem;
    height: 3rem;
    margin-top: 3rem;
  }
  .my-btn-wide {
    width: 14rem;
    height: 3rem;
    margin-top: 3rem;
  }
`;
