from playwright.sync_api import sync_playwright
import pathlib
def cap(html, sel, out):
    url = pathlib.Path(html).as_uri()
    with sync_playwright() as p:
        b = p.chromium.launch(executable_path="/opt/pw-browsers/chromium-1194/chrome-linux/chrome")
        pg = b.new_page(viewport={"width":1000,"height":1400})
        pg.goto(url); pg.query_selector(sel).screenshot(path=out); b.close()
cap("/home/claude/bank-example/oop_2. 은행 계좌 관리 시스템(콘솔).html", "#m5", "/home/claude/c5.png")
cap("/home/claude/bank-example/oop_3. 은행 계좌 관리 REST API(Spring Boot).html", "#m12", "/home/claude/s12.png")
print("done")
