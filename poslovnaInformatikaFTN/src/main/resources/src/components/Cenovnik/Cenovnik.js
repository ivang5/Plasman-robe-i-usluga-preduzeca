import React, { Component } from "react";
import CenovnikModal from "./CenovnikModal";
import styled from "styled-components";
import "../datatables.css";
import { Redirect } from "react-router-dom";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class Cenovnik extends Component {
  state = {
    cenovnici: [],
    proizvodi: [],
    noviCenovnik: {},
    selektovaniCenovnik: {},
    modalStavke: [],
    urlCenovnici: "http://localhost:8080/api/cenovnik/",
    urlProizvodi: "http://localhost:8080/api/proizvodi/",
    modalOpen: false,
    preuzetCenovnik: false,
    error: "",
    login: null,
  };

  // Preuzimanje svih cenovnika sa servera
  async getCenovnici() {
    try {
      const data = await fetch(this.state.urlCenovnici, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen nijedan cenovnik" };
        });
      } else {
        this.setState(() => {
          return { cenovnici: jsonData };
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
        let tempListaStavki = this.state.noviCenovnik.stavkeCenovnika;
        let proizvodi = jsonData;
        proizvodi.forEach((proizvod) => {
          var found = false;
          this.state.noviCenovnik.stavkeCenovnika.forEach((stavka) => {
            if (stavka.proizvod.id === proizvod.id) {
              found = true;
            }
          });
          if (!found) {
            var tempStavka = { proizvod: proizvod, cena: 0 };
            tempListaStavki.push(tempStavka);
          }
        });
        this.setState(() => {
          return { modalStavke: tempListaStavki };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje najnovijeg cenovnika sa servera
  async getNoviCenovnik() {
    try {
      const data = await fetch(this.state.urlCenovnici + "newest", {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjen najnoviji cenovnik" };
        });
      } else {
        this.setState(() => {
          return {
            noviCenovnik: jsonData,
            selektovaniCenovnik: jsonData,
            preuzetCenovnik: true,
          };
        });
        this.getProizvodi();
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
    this.getCenovnici();
    this.getNoviCenovnik().then(() => {
      if (this.state.preuzetCenovnik) {
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

  openModal = () => {
    this.setState({
      modalOpen: true,
    });
  };

  closeModal = () => {
    this.setState({
      modalOpen: false,
    });
  };

  saveModal = (promenjeneStavke) => {
    var stavke = this.state.noviCenovnik.stavkeCenovnika;
    promenjeneStavke.forEach((promenjenaStavka) => {
      var stavka = stavke.find((element) => element.id === promenjenaStavka.id);
      stavka.cena = promenjenaStavka.cena;
    });
    let date = new Date();
    const data = {
      datumIzdavanja: date,
      stavkeCenovnika: stavke,
    };
    fetch(this.state.urlCenovnici, {
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
        window.location.reload(false);
        alert("Cenovnik uspešno izdat!");
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
    this.closeModal();
  };

  formatDate = (dateStr) => {
    let date = new Date(dateStr);
    let formattedDate =
      date.getFullYear() +
      "-" +
      (date.getMonth() + 1) +
      "-" +
      date.getDate() +
      " " +
      date.getHours() +
      ":" +
      date.getMinutes() +
      ":" +
      date.getSeconds();
    return formattedDate;
  };

  handleDateChange = (e) => {
    let selectedDate = e.target.value;
    let cenovnik = this.state.cenovnici.find(
      (element) => this.formatDate(element.datumIzdavanja) === selectedDate
    );
    this.setState({
      selektovaniCenovnik: cenovnik,
    });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzetCenovnik) {
      return (
        <div>
          <DivWrapper id="selectContainer" className="form-group">
            <label htmlFor="selectCen">
              <h5>Izaberite cenovnik:</h5>
            </label>
            <select
              className="form-control"
              id="selectCen"
              onChange={this.handleDateChange}
            >
              {this.state.cenovnici.map((cenovnik) => {
                return (
                  <option key={cenovnik.id}>
                    {this.formatDate(cenovnik.datumIzdavanja)}
                  </option>
                );
              })}
            </select>
          </DivWrapper>
          <TableWrapper
            id="tabelaCena"
            className="col-11 mx-auto mt-5 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "35%" }}>Naziv</th>
                <th style={{ width: "20%" }}>Jedinica mere</th>
                <th style={{ width: "20%" }}>Tip</th>
                <th style={{ width: "25%" }}>Cena</th>
              </tr>
            </thead>
            <tbody>
              {this.state.selektovaniCenovnik.stavkeCenovnika.map((stavka) => {
                return (
                  <tr key={stavka.id}>
                    <td>{stavka.proizvod.naziv}</td>
                    <td>{stavka.proizvod.jedinicaMere}</td>
                    <td>{stavka.proizvod.tipProizvoda}</td>
                    <td>{stavka.cena}</td>
                  </tr>
                );
              })}
            </tbody>
          </TableWrapper>
          <ButtonWrapper
            className="btn btn-success float-right mt-4 px-4"
            onClick={this.openModal}
          >
            Promeni cenovnik
          </ButtonWrapper>
          <CenovnikModal
            stavke={this.state.modalStavke}
            modalOpen={this.state.modalOpen}
            saveModal={this.saveModal}
            closeModal={this.closeModal}
          />
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

const ButtonWrapper = styled.button`
  margin-right: 4.16%;
`;
