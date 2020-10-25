import React, { Component } from "react";
import styled from "styled-components";
import Korisnik from "./Korisnik";
import KorisnikModal from "./KorisnikModal";
import CreateKorisnikModal from "./CreateKorisnikModal";
import "../datatables.css";
import { Redirect } from "react-router-dom";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class Korisnici extends Component {
  state = {
    korisnici: [],
    urlKorisnici: "http://localhost:8080/api/zaposleni/",
    modalKorisnik: {},
    modalOpen: false,
    modalCreate: false,
    error: "",
    preuzetiKorisnici: false,
    login: null,
  };

  // Preuzimanje svih zaposlenih sa servera
  async getKorisnici() {
    try {
      const data = await fetch(this.state.urlKorisnici, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan korisnik" };
        });
      } else {
        this.setState(() => {
          return { korisnici: jsonData, preuzetiKorisnici: true };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  componentDidMount() {
    if (
      sessionStorage.getItem("token") === null ||
      sessionStorage.getItem("token") === undefined
    ) {
      this.setState({
        login: "/",
      });
    }
    this.getKorisnici().then(() => {
      if (this.state.preuzetiKorisnici) {
        this.$tableRef = $(this.tableRef);
        this.$tableRef.DataTable({
          bInfo: false,
          oLanguage: {
            sEmptyTable: "Nema dostupnih podataka u tabeli",
            sZeroRecords: "Nema rezultata pretrage",
            sSearch: "Pretraga:",
            oPaginate: {
              sNext: "Sledeća",
              sPrevious: "Prethodna",
            },
            sLengthMenu: "Prikaži _MENU_ redova",
          },
        });
      }
    });
  }

  // Brisanje korisnika iz tabele
  deleteKorisnik = (id) => {
    fetch(this.state.urlKorisnici + id, {
      method: "DELETE",
    })
      .then((res) => {
        res.text();
        console.log(res);
        this.getKorisnici();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  getKorisnik = (id) => {
    const korisnik = this.state.korisnici.find((item) => item.id === id);
    return korisnik;
  };

  openModal = (id) => {
    const korisnik = this.getKorisnik(id);
    this.setState({
      modalOpen: true,
      modalKorisnik: korisnik,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
      modalCreate: false,
    });
  };

  createModal = () => {
    this.setState({
      modalCreate: true,
    });
  };

  // Cuvanje izmena korisnika
  saveModal = (id, ime, prezime, adresa, brojTelefona, uloga) => {
    const korisnik = this.getKorisnik(id);

    const imeConst = ime === "" ? korisnik.ime : ime;
    const prezimeConst = prezime === "" ? korisnik.prezime : prezime;
    const adresaConst = adresa === "" ? korisnik.adresa : adresa;
    const brojTelefonaConst =
      brojTelefona === "" ? korisnik.brojTelefona : brojTelefona;
    const ulogaConst = uloga === "" ? korisnik.uloga : uloga;

    const data = {
      id: id,
      ime: imeConst,
      prezime: prezimeConst,
      adresa: adresaConst,
      brojTelefona: brojTelefonaConst,
      uloga: ulogaConst,
    };
    fetch(this.state.urlKorisnici, {
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
        this.getKorisnici();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  // Cuvanje novog korisnika
  createKorisnik = (ime, prezime, adresa, brojTelefona, uloga) => {
    const ulogaConst = uloga === "" ? "GENERALNI_DIREKTOR" : uloga;
    const data = {
      ime: ime,
      prezime: prezime,
      adresa: adresa,
      brojTelefona: brojTelefona,
      uloga: ulogaConst,
    };
    fetch(this.state.urlKorisnici, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token"),
      },
      body: JSON.stringify(data),
    })
      .then((res) => res.text())
      .then((res) => console.log(res))
      .then(this.closeModal())
      .then(() => this.getKorisnici())
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzetiKorisnici) {
      return (
        <div>
          <TableWrapper
            id="tabelaKorisnika"
            className="col-11 mt-5 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "20%" }}>Ime</th>
                <th style={{ width: "20%" }}>Prezime</th>
                <th style={{ width: "35%" }}>Adresa</th>
                <th style={{ width: "15%" }}>Broj telefona</th>
                <th style={{ width: "10%" }}>Uloga</th>
                <th>Izmena</th>
                <th>Brisanje</th>
              </tr>
            </thead>
            <tbody>
              {this.state.korisnici.map((korisnik) => {
                return (
                  <Korisnik
                    key={korisnik.id}
                    korisnik={korisnik}
                    deleteKorisnik={() => this.deleteKorisnik(korisnik.id)}
                    openModal={() => this.openModal(korisnik.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <ButtonWrapper
            className="btn btn-success float-right mt-4 px-4"
            onClick={this.createModal}
          >
            Dodaj korisnika
          </ButtonWrapper>
          <KorisnikModal
            modalOpen={this.state.modalOpen}
            modalKorisnik={this.state.modalKorisnik}
            closeModal={this.closeModal}
            saveModal={this.saveModal}
          />
          <CreateKorisnikModal
            modalCreate={this.state.modalCreate}
            closeModal={this.closeModal}
            createKorisnik={this.createKorisnik}
          />
        </div>
      );
    } else {
      return <React.Fragment />;
    }
  }
}

const TableWrapper = styled.table`
  th {
    border: 1px solid black;
    text-align: center;
    vertical-align: middle !important;
    padding: 1px !important;
    height: 2rem;
  }
`;

const ButtonWrapper = styled.button`
  margin-right: 4.16%;
`;
