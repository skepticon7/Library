export function categoryImageLinkGenerator(category : string)  {
  switch (category){
    case "History" : return "pillar.png";
    case "Fiction" : return "ufo.png";
    case "Religion" : return "coexistence.png";
    case "Mathematics" : return "calculator.png";
    case "Physics" : return "atom.png";
    case "Science" : return "research.png";
    case "Geography" : return "globe.png";
    default : return;
  }
}
