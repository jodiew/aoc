{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "scratch-dev-env";
  system = builtins.currentSystem;

  buildInputs = with pkgs; [
    zulu24
    clojure
  ];

  shellHook = ''

    echo "🚀 Development environment loaded!"
    echo
    echo "   java: $(java --version)"
    echo "clojure: $(clj --version)"
    echo
    echo "Ready to code! 💻"
  '';
}
