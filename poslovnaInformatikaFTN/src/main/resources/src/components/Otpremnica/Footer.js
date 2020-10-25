import React, { Component } from "react";

export default class Footer extends Component {
  render() {
    return (
      <div className="text-center">
        <hr
          className="col-11"
          style={{ borderWidth: "2px", borderColor: "var(--mainDark)" }}
        />
        <p>
          U slučaju plaćanja posle roka zaračunavamo zakonsku zateznu kamatu.
          <br />
          Za eventualne sporove priznaje se nadležnost Privrednog suda u
          Zrenjaninu.
        </p>
        <br />
        <div className="row">
          <div className="col-10 col-md-5">Za kupca</div>
          <div className="col-10 col-md-5">Za KETIN Z.Z.</div>
        </div>
      </div>
    );
  }
}
