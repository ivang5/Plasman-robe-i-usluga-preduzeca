import React, { Component } from "react";
import styled from "styled-components";
import "../../datatables.css";
import { Redirect } from "react-router-dom";
import PdvKategorija from "./PdvKategorija";
import PdvKategorijaModal from "./PdvKategorijaModal";
import CreatePdvKategorijaModal from "./CreatePdvKategorijaModal";

const $ = require("jquery");
$.DataTable = require("datatables.net");

export default class PdvKategorije extends Component {
  state = {
    pdvKategorije: [],
    grupeProizvoda: [],
    katGrupeProizvoda: [],
    pdvStope: [],
    urlPdvKategorije: "http://localhost:8080/api/pdvKategorije/",
    urlPdvStope: "http://localhost:8080/api/pdvKategorije/stope/",
    urlGrupeProizvoda: "http://localhost:8080/api/grupeProizvoda/",
    modalOpen: false,
    modalCreate: false,
    modalPdvKategorija: {},
    error: "",
    preuzetePdvKategorije: false,
    preuzeteGrupe: false,
    login: null,
  };

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
          return { pdvKategorije: jsonData, preuzetePdvKategorije: true };
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
          return { grupeProizvoda: jsonData, preuzeteGrupe: true };
        });
      }
    } catch (error) {
      console.log(error);
    }
  }

  // Preuzimanje svih PDV stopa sa servera
  async getPdvStope() {
    try {
      const data = await fetch(this.state.urlPdvStope, {
        headers: {
          "Authorization": sessionStorage.getItem("token"),
        },
      });
      const jsonData = await data.json();

      if (jsonData.length === 0) {
        this.setState(() => {
          return { error: "Greska, nije pronadjena nijedna PDV stopa" };
        });
      } else {
        this.setState(() => {
          return { pdvStope: jsonData };
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
    this.getGrupe();
    this.getPdvStope();
    this.getPdvKategorije().then(() => {
      if (this.state.preuzetePdvKategorije && this.state.preuzeteGrupe) {
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

  // Brisanje PDV kategorije iz tabele
  deletePdvKategorija = (id) => {
    fetch(this.state.urlPdvKategorije + id, {
      method: "DELETE",
    })
      .then((res) => res.text())
      .then((res) => console.log(res))
      .then(() => this.getPdvKategorije())
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  getPdvKategorija = (id) => {
    const pdvKategorija = this.state.pdvKategorije.find(
      (item) => item.id === id
    );
    return pdvKategorija;
  };

  getGrupeProizvoda = (id) => {
    var grupe = this.state.grupeProizvoda.filter(
      (element) => element.idPdvKategorije === id
    );
    return grupe;
  };

  getGrupaProizvoda = (id) => {
    const grupaProizvoda = this.state.grupeProizvoda.find(
      // eslint-disable-next-line
      (item) => item.id == id
    );
    return grupaProizvoda;
  };

  getPdvStopa = (id) => {
    // eslint-disable-next-line
    const pdvStopa = this.state.pdvStope.find((item) => item.id == id);
    return pdvStopa;
  };

  openModal = (id) => {
    const pdvKategorija = this.getPdvKategorija(id);
    const grupeProizvoda = this.getGrupeProizvoda(id);
    this.setState({
      modalOpen: true,
      modalPdvKategorija: pdvKategorija,
      katGrupeProizvoda: grupeProizvoda,
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

  saveModal = (id, naziv, grupeProizvoda, pdvStopaId, pdvStopa, dateStopa) => {
    var tempNaziv = naziv === "" ? this.state.modalPdvKategorija.naziv : naziv;
    var tempGrupeProizvoda = [];
    if (grupeProizvoda.length === 0) {
      tempGrupeProizvoda = this.state.modalPdvKategorija.grupeProizvoda;
    } else {
      grupeProizvoda.forEach((element) => {
        const tempGrupaProizvoda = this.getGrupaProizvoda(element);
        tempGrupeProizvoda.push(tempGrupaProizvoda);
      });
    }
    var tempPdvStopa = {};
    var tempPdvStope = [];
    if (pdvStopaId === 0) {
      if (pdvStopa === 0 || dateStopa === "") {
        tempPdvStope = this.state.modalPdvKategorija.stopePDV;
      } else {
        tempPdvStopa = {
          procenat: pdvStopa,
          datumPocetkaVazenja: dateStopa,
        };
      }
    } else {
      tempPdvStopa = this.getPdvStopa(pdvStopaId);
      tempPdvStope.push(tempPdvStopa);
    }
    const data = {
      id: id,
      naziv: tempNaziv,
      grupeProizvoda: tempGrupeProizvoda,
      stopePDV: tempPdvStope,
    };
    fetch(this.state.urlPdvKategorije, {
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
        this.getPdvKategorije();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  createPdvKategorija = (
    naziv,
    grupeProizvoda,
    pdvStopaId,
    pdvStopa,
    dateStopa
  ) => {
    var novaStopa = true;
    if (naziv === "" || grupeProizvoda.length === 0) {
      alert("Niste popunili sva potrebna polja!");
      return;
    } else if (pdvStopaId === 0) {
      if (pdvStopa === 0 || dateStopa === "") {
        alert("Niste popunili sva potrebna polja!");
      }
    } else {
      novaStopa = false;
    }
    var tempGrupeProizvoda = [];
    grupeProizvoda.forEach((element) => {
      const tempGrupaProizvoda = this.getGrupaProizvoda(element);
      tempGrupeProizvoda.push(tempGrupaProizvoda);
    });
    var tempPdvStopa = {};
    var tempPdvStope = [];
    if (novaStopa) {
      tempPdvStopa = {
        procenat: pdvStopa,
        datumPocetkaVazenja: dateStopa,
      };
      tempPdvStope.push(tempPdvStopa);
    } else {
      tempPdvStopa = this.getPdvStopa(pdvStopaId);
      tempPdvStope.push(tempPdvStopa);
    }
    const data = {
      naziv: naziv,
      grupeProizvoda: tempGrupeProizvoda,
      stopePDV: tempPdvStope,
    };
    fetch(this.state.urlPdvKategorije, {
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
        this.closeModal();
        this.getPdvKategorije();
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  render() {
    if (this.state.login) {
      return <Redirect to={this.state.login} />;
    }
    if (this.state.preuzetePdvKategorije && this.state.preuzeteGrupe) {
      return (
        <div>
          <TableWrapper
            id="tabelaPdvKategorija"
            className="col-11 mx-auto mt-5 table table-bordered"
            ref={(tableRef) => (this.tableRef = tableRef)}
          >
            <thead>
              <tr>
                <th style={{ width: "22%" }}>Naziv</th>
                <th style={{ width: "70%" }}>Grupe proizvoda</th>
                <th style={{ width: "8%" }}>PDV Stopa</th>
                <th>Izmena</th>
                <th>Brisanje</th>
              </tr>
            </thead>
            <tbody>
              {this.state.pdvKategorije.map((kategorija) => {
                const grupe = this.state.grupeProizvoda.filter(
                  (grupa) => grupa.idPdvKategorije === kategorija.id
                );
                return (
                  <PdvKategorija
                    key={kategorija.id}
                    kategorija={kategorija}
                    grupe={grupe}
                    deletePdvKategorija={() =>
                      this.deletePdvKategorija(kategorija.id)
                    }
                    openModal={() => this.openModal(kategorija.id)}
                  />
                );
              })}
            </tbody>
          </TableWrapper>
          <ButtonWrapper
            className="btn btn-success float-right mt-4 px-4"
            onClick={this.createModal}
          >
            Dodaj PDV kategoriju
          </ButtonWrapper>
          <PdvKategorijaModal
            modalOpen={this.state.modalOpen}
            modalPdvKategorija={this.state.modalPdvKategorija}
            grupeProizvoda={this.state.grupeProizvoda}
            katGrupeProizvoda={this.state.katGrupeProizvoda}
            pdvStope={this.state.pdvStope}
            closeModal={this.closeModal}
            saveModal={this.saveModal}
          />
          <CreatePdvKategorijaModal
            modalCreate={this.state.modalCreate}
            grupeProizvoda={this.state.grupeProizvoda}
            pdvStope={this.state.pdvStope}
            closeModal={this.closeModal}
            createPdvKategorija={this.createPdvKategorija}
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
