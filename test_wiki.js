async function f() {
    const title = "Toyota Corolla";
    const url = `https://en.wikipedia.org/w/api.php?action=query&titles=${encodeURIComponent(title)}&prop=pageimages&format=json&pithumbsize=800&redirects=1`;
    const r = await fetch(url);
    const d = await r.json();
    const pages = d.query.pages;
    const pageId = Object.keys(pages)[0];
    const image = pages[pageId].thumbnail ? pages[pageId].thumbnail.source : null;
    console.log(`Image for ${title}: ${image}`);
}
f();
