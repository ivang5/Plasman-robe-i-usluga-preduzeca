import React, { Component } from "react";
import styled from "styled-components";
import Predracun from "./Predracun";
import Faktura from "./Faktura";
import "../datatables.css";
import { Redirect } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class PoslovneGodine extends Component {
  state = {
    poslovneGodine: [],
    ukupanPorez: 0,
    predracuni: [],
    filtriraniPredracuni: [],
    fakture: [],
    filtriraneFakture: [],
    urlPoslovneGodine: "http://localhost:8080/api/preduzece/poslovneGodine",
    urlPredracuni: "http://localhost:8080/api/predracuni/",
    urlFakture: "http://localhost:8080/api/fakture/",
    preuzetePoslovneGodine: false,
    preuzetiPredracuni: false,
    preuzeteFakture: false,
    login: null,
    redirect: null,
    poslovnaGodina: new Date(),
    error: "",
  };

  // Preuzimanje svih poslovnih godina sa servera
  async getPoslovneGodine() {
    try {
      const data = await fetch(this.state.urlPoslovneGodine, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna poslovna godina" };
        });
      } else {
        this.setState(() => {
          return {
            poslovneGodine: jsonData,
            preuzetePoslovneGodine: true,
          };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

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
            filtriraniPredracuni: jsonData,
            preuzetiPredracuni: true,
          };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih izdatih faktura sa servera
  async getFakture() {
    try {
      const data = await fetch(this.state.urlFakture, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna faktura" };
        });
      } else {
        this.setState(() => {
          return {
            fakture: jsonData,
            filtriraneFakture: jsonData,
            preuzeteFakture: true,
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
    this.getPoslovneGodine();
    this.getFakture();
    this.getPredracuni().then(() => {
      if (
        this.state.preuzetiPredracuni &&
        this.state.preuzeteFakture &&
        this.state.preuzetePoslovneGodine
      ) {
        this.$tablePredracunRef = $(this.tablePredracunRef);
        this.$tablePredracunRef.DataTable({
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
        this.$tableFakturaRef = $(this.tableFakturaRef);
        this.$tableFakturaRef.DataTable({
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

  handleGodinaChange = (e) => {
    const selectedYear = new Date(e).getFullYear();
    const filteredPredracuni = this.state.predracuni.filter(
      // eslint-disable-next-line
      (element) => element.poslovnaGodina == selectedYear
    );
    const filteredFakture = this.state.fakture.filter(
      // eslint-disable-next-line
      (element) => element.poslovnaGodina == selectedYear
    );
    // eslint-disable-next-line
    const poslovnaGodina = this.state.poslovneGodine.find(
      // eslint-disable-next-line
      (element) => element.godina == selectedYear
    );
    this.setState({
      poslovnaGodina: e,
      filtriraniPredracuni: filteredPredracuni,
      filtriraneFakture: filteredFakture,
      // Izbacuje grešku zato što je ukupanPorez null
      // ukupanPorez: poslovnaGodina.ukupanPorez,
    });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    } else if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    if (
      this.state.preuzetiPredracuni &&
      this.state.preuzeteFakture &&
      this.state.preuzetePoslovneGodine
    ) {
      return (
        <div>
          <DivWrapper className="form-group">
            <label htmlFor="selectCen">
              <h5>Poslovna godina:</h5>
            </label>
            <DatePicker
              className="form-control date-width"
              selected={this.state.poslovnaGodina}
              onChange={this.handleGodinaChange}
              showYearPicker
              name="poslovnaGodina"
              dateFormat="yyyy"
            />
            <h5 className="mt-3">Ukupan porez:</h5>
            <input
              type="text"
              className="form-control"
              value={this.state.ukupanPorez}
              readOnly
            />
          </DivWrapper>
          <hr />
          <br />
          <h3 style={{ marginLeft: "4.16%" }}>Predračuni</h3>
          <TableWrapper
            id="tabelaPredracuna"
            className="col-11 mx-auto table table-bordered"
            ref={(tablePredracunRef) =>
              (this.tablePredracunRef = tablePredracunRef)
            }
          >
            <thead>
              <tr>
                <th style={{ width: "30%" }}>Stavke predračuna</th>
                <th style={{ width: "20%" }}>Kupac</th>
                <th style={{ width: "10%" }}>Iznos</th>
                <th style={{ width: "10%" }}>PDV</th>
                <th style={{ width: "10%" }}>Ukupan rabat</th>
                <th style={{ width: "10%" }}>Rok isporuke</th>
                <th style={{ width: "10%" }}>Rok plaćanja</th>
              </tr>
            </thead>
            <tbody>
              {this.state.filtriraniPredracuni.map((predracun) => {
                return <Predracun key={predracun.id} predracun={predracun} />;
              })}
            </tbody>
          </TableWrapper>
          <br />
          <hr />
          <br />
          <h3 style={{ marginLeft: "4.16%" }}>Fakture</h3>
          <TableWrapper
            id="tabelaFaktura"
            className="col-11 mx-auto table table-bordered"
            ref={(tableFakturaRef) => (this.tableFakturaRef = tableFakturaRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "30%" }}>Stavke fakture</th>
                <th style={{ width: "20%" }}>Kupac</th>
                <th style={{ width: "10%" }}>Datum</th>
                <th style={{ width: "10%" }}>Iznos</th>
                <th style={{ width: "10%" }}>PDV</th>
                <th style={{ width: "10%" }}>Ukupan rabat</th>
                <th style={{ width: "10%" }}>Status</th>
              </tr>
            </thead>
            <tbody>
              {this.state.filtriraneFakture.map((faktura) => {
                return <Faktura key={faktura.id} faktura={faktura} />;
              })}
            </tbody>
          </TableWrapper>
          <br />
          <hr />
          <br />
        </div>
      );
    } else {
      return <React.Fragment />;
    }
  }
}

const DivWrapper = styled.div`
  margin-top: 1rem;
  margin-left: 4.16%;
  width: 12rem;
`;

const TableWrapper = styled.table`
  th {
    border: 1px solid black;
    text-align: center;
    vertical-align: middle !important;
    padding: 1px !important;
    height: 2rem;
  }
`;
