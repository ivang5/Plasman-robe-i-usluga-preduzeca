import React, { Component } from "react";
import styled from "styled-components";
import Faktura from "./Faktura";
import FakturaModal from "./FakturaModal";
import "../../datatables.css";
import { Redirect } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class KnjigaIzdatihFaktura extends Component {
  state = {
    fakture: [],
    flitriraneFakture: [],
    urlFakture: "http://localhost:8080/api/fakture/",
    modalOpen: false,
    modalFaktura: {},
    preuzeteFakture: false,
    login: null,
    startDate: new Date("2020/01/01"),
    endDate: new Date("2030/01/01"),
    racunOtpremnica: {},
    error: "",
  };

  // Preuzimanje svih izdatih faktura sa servera
  async getFakture() {
    try {
      const data = await fetch(this.state.urlFakture, {
        headers: {
          "X-Auth-Token": sessionStorage.getItem("token"),
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
            flitriraneFakture: jsonData,
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
    this.getFakture().then(() => {
      if (this.state.preuzeteFakture) {
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

  getFaktura = (id) => {
    const faktura = this.state.fakture.find((item) => item.id === id);
    return faktura;
  };

  openModal = (id) => {
    const faktura = this.getFaktura(id);
    this.setState({
      modalOpen: true,
      modalFaktura: faktura,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
    });
  };

  handleStartDateChange = (e) => {
    this.setState(
      {
        startDate: e,
      },
      () => this.getFaktureByDate()
    );
  };

  handleEndDateChange = (e) => {
    this.setState(
      {
        endDate: e,
      },
      () => this.getFaktureByDate()
    );
  };

  getFaktureByDate = () => {
    let filtriraneFakture = this.state.fakture.filter(
      (faktura) =>
        new Date(faktura.datumFakture) > new Date(this.state.startDate) &&
        new Date(faktura.datumFakture) < new Date(this.state.endDate)
    );
    this.setState({
      flitriraneFakture: filtriraneFakture,
    });
  };

  async generateRacunOtpremnica(faktura) {
    const data = {
      idIdavaoca: faktura.idIdavaoca,
      stavkeFakture: faktura.stavkeFakture,
      datumFakture: faktura.datumFakture,
      datumValute: faktura.datumValute,
      brojRacuna: faktura.brojRacuna,
      iznos: faktura.iznos,
      pdv: faktura.pdv,
      ukupanRabat: faktura.ukupanRabat,
      status: faktura.status,
      kupac: faktura.kupac,
      pozivNaBroj: faktura.pozivNaBroj,
      poslovnaGodina: faktura.poslovnaGodina,
    };
    try {
      const recievedData = await fetch(
        this.state.urlFakture + "generateRacunOtpremnica",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-Auth-Token": sessionStorage.getItem("token"),
          },
          body: JSON.stringify(data),
        }
      );
      const jsonData = await recievedData.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen racun" };
        });
      } else {
        this.setState(
          () => {
            return { racunOtpremnica: jsonData };
          },
          () => this.getRacun()
        );
      }
    } catch (error) {
      console.log(error);
    }
  }

  async getRacun() {
    let racunOtpremnica = this.state.racunOtpremnica;
    const data = {
      id: this.state.modalFaktura.id,
      stavkeRacuna: racunOtpremnica.stavkeRacuna,
      datumRacuna: racunOtpremnica.datumRacuna,
      brojRacunaPlacanja: racunOtpremnica.brojRacunaPlacanja,
      iznos: racunOtpremnica.iznos,
      pdv: racunOtpremnica.pdv,
      kupac: racunOtpremnica.kupac,
      pozivNaBroj: racunOtpremnica.pozivNaBroj,
      prodavac: racunOtpremnica.prodavac,
    };
    try {
      // eslint-disable-next-line
      const recievedData = await fetch(
        this.state.urlFakture + "downloadRacun",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-Auth-Token": sessionStorage.getItem("token"),
          },
          body: JSON.stringify(data),
        }
      ).then((res) => {
        res.blob().then((blob) => {
          let url = window.URL.createObjectURL(blob);
          let a = document.createElement("a");
          a.href = url;
          a.download = "racun-otpremnica.pdf";
          a.click();
        });
      });
    } catch (error) {
      console.log(error);
    }
  }

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzeteFakture) {
      return (
        <div>
          <DivWrapper className="form-group">
            <label htmlFor="selectCen">
              <h5>Datum od:</h5>
            </label>
            <DatePicker
              className="form-control"
              selected={this.state.startDate}
              onChange={this.handleStartDateChange}
              selectsStart
              startDate={this.state.startDate}
              endDate={this.state.endDate}
              name="startDate"
              dateFormat="dd/MM/yyyy"
            />
          </DivWrapper>
          <DivWrapper className="form-group">
            <label htmlFor="selectCen">
              <h5>Datum do:</h5>
            </label>
            <DatePicker
              className="form-control"
              selected={this.state.endDate}
              onChange={this.handleEndDateChange}
              selectsEnd
              startDate={this.state.startDate}
              endDate={this.state.endDate}
              name="startDate"
              dateFormat="dd/MM/yyyy"
            />
          </DivWrapper>
          <hr style={{ width: "91.67%" }} />
          <TableWrapper
            id="tabelaFaktura"
            className="col-11 mx-auto table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
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
                <th>Detalji</th>
              </tr>
            </thead>
            <tbody>
              {this.state.flitriraneFakture.map((faktura) => {
                return (
                  <Faktura
                    key={faktura.id}
                    faktura={faktura}
                    openModal={() => this.openModal(faktura.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <FakturaModal
            modalOpen={this.state.modalOpen}
            closeModal={this.closeModal}
            modalFaktura={this.state.modalFaktura}
            generateRacunOtpremnica={() =>
              this.generateRacunOtpremnica(this.state.modalFaktura)
            }
          />
        </div>
      );
    } else {
      return <React.Fragment />;
    }
  }
}

const DivWrapper = styled.div`
  display: inline-block;
  margin-top: 2rem;
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
