import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default class PdvKategorijaModal extends Component {
  state = {
    inputNaziv: "",
    inputGrupa: "",
    inputGrupaId: 0,
    inputPdvStopaId: 0,
    grupeProizvoda: [],
    grupeProizvodaToShow: [],
    pdvStopa: 0,
    dateStopa: new Date(),
    deletedOption: false,
  };

  handleNazivChange = (e) => {
    this.setState({
      inputNaziv: e.target.value,
    });
  };

  handleGrupaChange = (e) => {
    var index = e.nativeEvent.target.selectedIndex;
    this.setState({
      inputGrupa: e.nativeEvent.target[index].text,
      inputGrupaId: e.target.value,
    });
  };

  handlePdvStopaIdChange = (e) => {
    if (!this.state.deletedOption) {
      document.getElementById("pdvSelect").remove(0);
    }
    this.setState({
      inputPdvStopaId: e.target.value,
      pdvStopa: 0,
      dateStopa: "",
      deletedOption: true,
    });
    document.getElementById("stopaInput").value = "";
  };

  handlePdvStopaChange = (e) => {
    this.setState({
      pdvStopa: e.target.value,
      inputPdvStopaId: 0,
    });
    document.getElementById("pdvSelect").selectedIndex = 0;
  };

  handleDateStopaChange = (e) => {
    this.setState({
      dateStopa: e,
      inputPdvStopaId: 0,
    });
    document.getElementById("pdvSelect").selectedIndex = 0;
  };

  addGrupa = () => {
    if (this.state.inputGrupaId !== 0) {
      var grupaFound = false;
      this.state.grupeProizvodaToShow.forEach((element) => {
        if (element === this.state.inputGrupa) {
          grupaFound = true;
        }
      });
      if (grupaFound) {
        alert("Izabrana grupa je već dodata!");
      } else {
        let tempGrupe = this.state.grupeProizvoda;
        let tempGrupeToShow = this.state.grupeProizvodaToShow;
        tempGrupe.push(this.state.inputGrupaId);
        tempGrupeToShow.push(this.state.inputGrupa);
        this.setState({
          grupeProizvoda: tempGrupe,
          grupeProizvodaToShow: tempGrupeToShow,
        });
      }
    }
  };

  clearGrupe = () => {
    this.setState({
      grupeProizvoda: [],
      grupeProizvodaToShow: [],
    });
  };

  render() {
    const {
      modalOpen,
      closeModal,
      saveModal,
      grupeProizvoda,
      katGrupeProizvoda,
      pdvStope,
    } = this.props;
    const { id, naziv, stopePDV } = this.props.modalPdvKategorija;
    var grupeProizvodaStr = "";
    katGrupeProizvoda.forEach((element) => {
      grupeProizvodaStr +=
        grupeProizvodaStr === "" ? element.naziv : ", " + element.naziv;
    });
    if (!modalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div id="modal" className="col-8 mx-auto text-center p-5">
                <h3>Izmena</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  Naziv: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={naziv}
                    onChange={this.handleNazivChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  Grupe proizvoda: <br />{" "}
                  <div className="input-group form-group">
                    <textarea
                      type="text"
                      className="form-control"
                      value={this.state.grupeProizvodaToShow}
                      readOnly
                    ></textarea>
                    <select
                      id="my-select"
                      type="text"
                      className="form-control"
                      onChange={this.handleGrupaChange}
                    >
                      <option disabled selected value>
                        {" "}
                        -- Izaberite grupu proizvoda --{" "}
                      </option>
                      {grupeProizvoda.map((element) => (
                        <option key={element.id} value={element.id}>
                          {element.naziv}
                        </option>
                      ))}
                    </select>
                    <button className="btn-add ml-2" onClick={this.addGrupa}>
                      <i className="fas fa-plus" />
                    </button>
                    <button
                      className="btn-clear ml-2"
                      onClick={this.clearGrupe}
                    >
                      <i className="fas fa-trash" />
                    </button>
                  </div>
                </h5>
                <h5 className="mb-3">
                  PDV stopa: <br />{" "}
                  <div className="input-group form-group">
                    <div className="mt-3">
                      <h6>Izaberite postojeću</h6>
                      <select
                        id="pdvSelect"
                        type="text"
                        className="form-control"
                        onChange={this.handlePdvStopaIdChange}
                      >
                        <option selected value>
                          {stopePDV[0].procenat + "%"}
                        </option>
                        {pdvStope.map((element) => (
                          <option key={element.id} value={element.id}>
                            {element.procenat + "%"}
                          </option>
                        ))}
                      </select>
                    </div>
                    <div className="margin-horizontal mt-3">
                      <h6>ili</h6>
                      <p className="text-small">
                        Važeće će biti ono <br /> što poslednje promenite
                      </p>
                    </div>
                    <div className="mt-3">
                      <h6>Napravite novu</h6>
                      <input
                        id="stopaInput"
                        type="number"
                        className="form-control"
                        placeholder="stopa PDV-a"
                        onChange={this.handlePdvStopaChange}
                      ></input>
                      <p className="text-very-small">
                        (PDV stopa izražena u procentima)
                      </p>
                      <DatePicker
                        id="datumInput"
                        className="form-control"
                        selected={this.state.dateStopa}
                        onChange={this.handleDateStopaChange}
                        name="date"
                        dateFormat="dd/MM/yyyy"
                      />
                      <p className="text-very-small">(datum početka važenja)</p>
                    </div>
                  </div>
                </h5>
                <Link to="/pdv-kategorije">
                  <button
                    className="btn btn-success m-4"
                    onClick={() => {
                      saveModal(
                        id,
                        this.state.inputNaziv,
                        this.state.grupeProizvoda,
                        this.state.inputPdvStopaId,
                        this.state.pdvStopa,
                        this.state.dateStopa
                      );
                      this.setState({
                        inputNaziv: "",
                        inputGrupa: "",
                        inputGrupaId: 0,
                        inputPdvStopaId: 0,
                        grupeProizvoda: [],
                        grupeProizvodaToShow: [],
                        pdvStopa: 0,
                        dateStopa: new Date(),
                        deletedOption: false,
                      });
                    }}
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/pdv-kategorije">
                  <button
                    className="btn btn-danger m-4"
                    onClick={() => {
                      closeModal();
                      this.setState({
                        inputNaziv: "",
                        inputGrupa: "",
                        inputGrupaId: 0,
                        inputPdvStopaId: 0,
                        grupeProizvoda: [],
                        grupeProizvodaToShow: [],
                        pdvStopa: 0,
                        dateStopa: new Date(),
                        deletedOption: false,
                      });
                    }}
                  >
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
  textarea {
    max-height: 12rem;
  }
  #my-select {
    margin-left: 0.5rem;
  }
  .btn-add {
    color: var(--mainDark);
    width: 2.25rem;
    height: 2.25rem;
    background-color: var(--lightGreen);
    border-radius: 50%;
  }
  .btn-add: hover {
    background-color: var(--mainGreen);
  }
  .btn-clear {
    color: var(--mainDark);
    width: 2.25rem;
    height: 2.25rem;
    background-color: var(--lightRed);
    border-radius: 50%;
  }
  .btn-clear: hover {
    background-color: var(--mediumRed);
  }
  .margin-horizontal {
    margin: 0rem 5.69rem;
  }
  .text-small {
    font-size: 13px;
    margin-top: 2.7rem;
  }
  .text-very-small {
    font-size: 11px;
  }
`;
