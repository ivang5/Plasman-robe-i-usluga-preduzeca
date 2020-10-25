import React, { Component } from "react";
import styled from "styled-components";
import Predracun from "./Predracun";
import PredracunModal from "./PredracunModal";
import "../../datatables.css";
import { Redirect } from "react-router-dom";
import GenerisanjeFaktureModal from "./GenerisanjeFaktureModal";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class KnjigaIzdatihPredracuna extends Component {
  state = {
    predracuni: [],
    urlPredracuni: "http://localhost:8080/api/predracuni/",
    modalOpen: false,
    fakGenModalOpen: false,
    modalPredracun: {},
    preuzetiPredracuni: false,
    login: null,
    redirect: null,
  };

  // Preuzimanje svih izdatih predracuna sa servera
  async getPredracuni() {
    try {
      const data = await fetch(this.state.urlPredracuni, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan predracun" };
        });
      } else {
        this.setState(() => {
          return {
            predracuni: jsonData,
            preuzetiPredracuni: true,
          };
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
    this.getPredracuni().then(() => {
      if (this.state.preuzetiPredracuni) {
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

  getPredracun = (id) => {
    const predracun = this.state.predracuni.find((item) => item.id === id);
    return predracun;
  };

  openModal = (id) => {
    const predracun = this.getPredracun(id);
    this.setState({
      modalOpen: true,
      modalPredracun: predracun,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
    });
  };

  openFakGenModal = () => {
    this.setState({
      fakGenModalOpen: true,
    });
  };

  closeFakGenModal = () => {
    this.setState({
      fakGenModalOpen: false,
    });
  };

  generisiFakturu = (pozivNaBroj) => {
    let predracun = this.state.modalPredracun;
    const data = {
      idIzdavaoca: predracun.idIzdavaoca,
      stavkePredracuna: predracun.stavkePredracuna,
      rokIsporuke: predracun.rokIsporuke,
      rokPlacanja: predracun.rokPlacanja,
      brojRacuna: predracun.brojRacuna,
      iznos: predracun.iznos,
      pdv: predracun.pdv,
      ukupanRabat: predracun.ukupanRabat,
      kupac: predracun.kupac,
      poslovnaGodina: predracun.poslovnaGodina,
    };
    fetch(this.state.urlPredracuni + "generateFaktura", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": sessionStorage.getItem("token"),
        "poziv-na-broj": pozivNaBroj,
      },
      body: JSON.stringify(data),
    })
      .then((res) => {
        res.text();
        console.log(res);
        alert("Faktura uspešno generisana!");
        this.setState({
          brojStavki: 0,
          redirect: "/knjiga-faktura",
        });
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    } else if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    if (this.state.preuzetiPredracuni) {
      return (
        <div>
          <TableWrapper
            id="tabelaPredracuna"
            className="col-11 mx-auto table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "30%" }}>Stavke predracuna</th>
                <th style={{ width: "20%" }}>Kupac</th>
                <th style={{ width: "10%" }}>Iznos</th>
                <th style={{ width: "10%" }}>PDV</th>
                <th style={{ width: "10%" }}>Ukupan rabat</th>
                <th style={{ width: "10%" }}>Rok isporuke</th>
                <th style={{ width: "10%" }}>Rok plaćanja</th>
                <th>Detalji</th>
              </tr>
            </thead>
            <tbody>
              {this.state.predracuni.map((predracun) => {
                return (
                  <Predracun
                    key={predracun.id}
                    predracun={predracun}
                    openModal={() => this.openModal(predracun.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <PredracunModal
            modalOpen={this.state.modalOpen}
            closeModal={this.closeModal}
            modalPredracun={this.state.modalPredracun}
            openFakGenModal={this.openFakGenModal}
          />
          <GenerisanjeFaktureModal
            fakGenModalOpen={this.state.fakGenModalOpen}
            closeFakGenModal={this.closeFakGenModal}
            generisiFakturu={this.generisiFakturu}
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
